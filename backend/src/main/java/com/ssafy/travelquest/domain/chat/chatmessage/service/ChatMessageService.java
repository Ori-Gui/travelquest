package com.ssafy.travelquest.domain.chat.chatmessage.service;

import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatMessageDto;
import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatMessageSaveDto;
import com.ssafy.travelquest.domain.chat.chatmessage.dto.ChatUserDto;
import com.ssafy.travelquest.domain.chat.chatmessage.dto.PartyMemberMap;
import com.ssafy.travelquest.domain.chat.chatmessage.entity.ChatMessageDocument;
import com.ssafy.travelquest.domain.chat.chatmessage.repository.ChatMessageRepository;
import com.ssafy.travelquest.domain.party.entity.PartyMember;
import com.ssafy.travelquest.domain.party.repository.PartyMemberRepository;
import com.ssafy.travelquest.domain.state.dto.ChatRoomUserDto;
import com.ssafy.travelquest.domain.state.service.UserSessionService;
import com.ssafy.travelquest.domain.user.entity.JobCode;
import com.ssafy.travelquest.domain.user.entity.User;
import com.ssafy.travelquest.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final UserSessionService userSessionService;
    private final PartyMemberRepository partyMemberRepository;

    public void saveChatMessage(ChatMessageSaveDto message, Long partyId) {
        ChatMessageDocument chatMessageDocument = ChatMessageDocument.of(
                partyId,
                message.getUserId(),
                message.getMessage(),
                String.valueOf(LocalDateTime.now())
        );

        chatMessageRepository.save(chatMessageDocument);
    }

    public ChatUserDto userResolve(Long partyId, Long userId) {
        ChatRoomUserDto chatRoomUserDto = userSessionService.getPartyMemberByPartyIdAndUserId(partyId, userId);
        if (chatRoomUserDto != null) {
            return ChatUserDto.builder()
                    .name(chatRoomUserDto.getName())
                    .profileImage(chatRoomUserDto.getProfileImage())
                    .job(chatRoomUserDto.getJob())
                    .build();
        }

        User user = userService.getUser(userId);
        if (user == null) {
            return null;
        }

        return ChatUserDto.builder()
                .name(user.getUserName())
                .job(user.getJobClassCode())
                .build();
    }

    // ToDO: 들어오는 Before 값 이상함
    public List<ChatMessageDto> getMessages(
            Long chatRoomId,
            Long userId,
            LocalDateTime before,
            int limit
    ) {
        // 0) before 기본값
        before = LocalDateTime.now();
        log.debug("getMessages called → chatRoomId={}, userId={}, before={}, limit={}",
                chatRoomId, userId, before, limit);

        // 1) 파티 멤버 전체 배치 조회
        List<PartyMember> members = partyMemberRepository.getPartyMembersByPartyId(chatRoomId);
        log.debug("Party members fetched: count={}", members.size());
        Map<Long, PartyMember> memberMap = members.stream()
                .collect(Collectors.toMap(PartyMember::getUserId, pm -> pm));

        // 2) 조회 권한 검증 및 joinedAt
        PartyMember me = memberMap.get(userId);
        if (me == null) {
            log.warn("User {} not in chat room {}", userId, chatRoomId);
            throw new IllegalArgumentException("User not in chat room");
        }
        LocalDateTime joinedAt = me.getJoinedAt();
        log.debug("User {} joinedAt={}", userId, joinedAt);

        // 3) 파티 멤버 프로필 배치 조회
        List<Long> allUserIds = new ArrayList<>(memberMap.keySet());
        List<User> users = userService.getUsersByIds(allUserIds);
        log.debug("Batch fetched user profiles: count={}", users.size());
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        // 4) LocalDateTime을 String으로 변환 (MongoDB 쿼리용)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String joinedAtStr = joinedAt.format(formatter);
        String beforeStr = before.format(formatter);
        log.debug("Converted time strings → joinedAtStr={}, beforeStr={}", joinedAtStr, beforeStr);

        // 5) 메시지 페이지 조회 & DTO 변환 & 프로필 병합
        PageRequest page = PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "sentAt"));
        Page<ChatMessageDocument> pageResult = chatMessageRepository
                .findMessagesBetween(chatRoomId, joinedAtStr, beforeStr, page);

        log.debug("MongoDB fetched messages: totalElements={}, totalPages={}",
                pageResult.getTotalElements(), pageResult.getTotalPages());
        pageResult.getContent().forEach(doc ->
                log.debug("  ➡ messageId={}, sentAt={}, content={}",
                        doc.getId(), doc.getSentAt(), doc.getMessage())
        );

        List<ChatMessageDto> result = pageResult
                .map(ChatMessageDto::from)
                .getContent()
                .stream()
                .map(msg -> {
                    var builder = msg.toBuilder();
                    User u = userMap.get(msg.getUserId());
                    if (u != null) {
                        builder.name(u.getUserName())
                        .profileImage(u.getProfileImage())
                                .job(u.getJobClassCode());
                    } else {
                        builder.name("알수없음")
                                .job(JobCode.NONE);
                    }
                    return builder.build();
                })
                .collect(Collectors.toList());

        log.debug("Final result DTO count={}", result.size());
        return result;
    }

//    public List<ChatMessageDto> getMessages(Long chatRoomId, Long userId, LocalDateTime before, int limit) {
//        // before 없으면 기준을 현재시간 기준으로
//        if (before == null) before = LocalDateTime.now();
//
//        // 해당 채팅방에 있는 유저 전부 가져오기
//        Optional<PartyMember> target = Optional.empty();
//        List<PartyMember> targets = partyMemberRepository
//                .getPartyMembersByPartyId(chatRoomId);
//        // 유저 정보 맵 생성
//        Map<Long, PartyMemberMap> targetMap = targets.stream()
//                .collect(Collectors.toMap(
//                        PartyMember::getUserId,
//                        PartyMemberMap::of
//                ));
//        // 맵 마저 채우기 (유저 이름, 직업)
//        for (PartyMember partyMember : targets) {
//            User user = userService.getUser(partyMember.getUserId());
//            if (user != null) {
//                targetMap.put(partyMember.getUserId(), PartyMemberMap.from(
//                        partyMember.getUserId(),
//                        user.getUserName(),
//                        user.getJobClassCode()
//                ));
//            } else {
//                targetMap.put(partyMember.getUserId(), PartyMemberMap.from(
//                        partyMember.getUserId(),
//                        "알수없음",
//                        JobCode.NONE
//                ));
//            }
//        }
//
//        // 해당 유저가 채팅방에 있는지 확인
//        for(PartyMember targetMember : targets) {
//            if (targetMember.getUserId().equals(userId)) {
//                target = Optional.of(targetMember);
//                break;
//            }
//        }
//
//        // 해당 유저가 채팅방에 없으면 예외처리
//        if (target.isEmpty()) {
//            throw new IllegalArgumentException("User not found in chat room");
//        }
//
//        // 해당 유저의 joinedAt 가져오기
//        LocalDateTime joinedAt = target.get().getJoinedAt();
//
//        // 해당 유저의 joinedAt 이후에 보낸 메시지들 가져오기
//        PageRequest page = PageRequest.of(0, limit);
//        List<ChatMessageDto> content = chatMessageRepository
//                .findByChatRoomIdAndSentAtBetweenOrderBySentAtAsc(
//                        chatRoomId, joinedAt, before, page
//                ).map(ChatMessageDto::from).getContent();
//
//        // 가져온 메세지에 대해 유저 정보 추가
//        List<ChatMessageDto> result = new ArrayList<>();
//        for (ChatMessageDto chatMessageDto : content) {
//            PartyMemberMap partyMemberMap = targetMap.get(chatMessageDto.getUserId());
//            if (partyMemberMap != null) {
//                result.add(ChatMessageDto.from(chatMessageDto)
//                        .toBuilder()
//                        .name(partyMemberMap.getName())
//                        .job(partyMemberMap.getJob())
//                        .build());
//            } else {
//                result.add(chatMessageDto);
//            }
//        }
//
//        return result;
//    }
}
