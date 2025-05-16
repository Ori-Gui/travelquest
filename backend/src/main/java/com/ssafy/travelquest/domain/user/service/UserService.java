package com.ssafy.travelquest.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.travelquest.domain.oauth.entity.OAuthProvider;
import com.ssafy.travelquest.domain.oauth.service.OAuthService;
import com.ssafy.travelquest.domain.user.entity.User;
import com.ssafy.travelquest.domain.user.exception.NoSuchUserException;
import com.ssafy.travelquest.domain.user.repository.UserRepository;
import com.ssafy.travelquest.global.common.event.CreateUserEvent;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OAuthService oAuthService;
    private final ApplicationEventPublisher publisher;
    
    @Transactional
    public void createUser(String sub, OAuthProvider provider) {
        // sub와 provider를 이용하여 유저가 존재하는지 확인
        if (oAuthService.findWithUserBySubAndProvider(sub, provider).isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        // 유저 정보 생성
        User user = User.of();
        userRepository.insert(user); // no 필드에 자동 주입됨
        System.out.println("✅ 생성된 PK: " + user.getNo()); // 여기서 ID 꺼냄
        int targetId = user.getNo();
        User target = userRepository.findById(targetId);

        publisher.publishEvent(CreateUserEvent.builder()
                .userId(target.getNo())
                .sub(sub)
                .provider(provider)
                .build());
    }
    
    public User getUser(int no) {
        return userRepository.findById(no);
    }

    public Optional<User> getByUserId(String userId) {
        return Optional.of(userRepository.findByUserId(userId));
    }

    public void register(User user) {
        userRepository.insert(user);
    }

    public void modify(User user) {
        userRepository.update(user);
    }

    public void remove(int no) {
        userRepository.delete(no);
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public String findPassword(String userId, String userName, String email) {
        User user = userRepository.findByUserIdAndNameAndEmail(userId, userName, email);
        if (user == null) {
            throw new NoSuchUserException("일치하는 사용자가 없습니다.");
        }
        return user.getPassword();
    }

}
