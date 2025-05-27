package com.ssafy.travelquest.domain.user.service;

import com.ssafy.travelquest.domain.user.dto.MbtiAnswer;
import com.ssafy.travelquest.domain.user.dto.MbtiResultResponse;
import com.ssafy.travelquest.domain.user.dto.UserClearDungeonResponse;
import com.ssafy.travelquest.domain.user.dto.UserProfileEditRequest;
import com.ssafy.travelquest.domain.user.entity.JobClass;
import com.ssafy.travelquest.domain.user.entity.MBTI;
import com.ssafy.travelquest.domain.user.repository.JobClassRepository;
import com.ssafy.travelquest.domain.user.repository.RefreshTokenRepository;
import com.ssafy.travelquest.domain.user.utils.MBTICalculator;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.travelquest.domain.oauth.entity.OAuthProvider;
import com.ssafy.travelquest.domain.oauth.service.BlackListService;
import com.ssafy.travelquest.domain.oauth.service.OAuthService;
import com.ssafy.travelquest.domain.user.entity.User;
import com.ssafy.travelquest.domain.user.exception.NoSuchUserException;
import com.ssafy.travelquest.domain.user.repository.UserRepository;
import com.ssafy.travelquest.global.common.event.CreateUserEvent;
import com.ssafy.travelquest.global.common.jwt.JwtResolver;

import io.jsonwebtoken.Claims;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JobClassRepository jobClassRepository;
    private final OAuthService oAuthService;
    private final ApplicationEventPublisher publisher;
    private final BlackListService blackListService;
    private final JwtResolver jwtResolver;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void createUser(String sub, OAuthProvider provider) {
        // sub와 provider를 이용하여 유저가 존재하는지 확인
        if (oAuthService.findWithUserBySubAndProvider(sub, provider).isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        // 유저 정보 생성
        User user = User.of();
        userRepository.insert(user); // no 필드에 자동 주입됨
        Long targetId = user.getId();
        User target = userRepository.findById(targetId);

        publisher.publishEvent(CreateUserEvent.builder()
                .userId(target.getId())
                .sub(sub)
                .provider(provider)
                .build());
    }
    
    public User getUser(Long no) {
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
    
    @Transactional
    public void registUser(Long userId, UserProfileEditRequest userProfileEditRequest) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NoSuchUserException("사용자를 찾을 수 없음");
        }

        User updateUser = User.regist(user, userProfileEditRequest);

        userRepository.update(updateUser);
    }

    @Transactional
    public void editUserProfile(Long userId, UserProfileEditRequest userProfileEditRequest) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NoSuchUserException("사용자를 찾을 수 없음");
        }

        User updateUser = User.update(user, userProfileEditRequest);

        userRepository.update(updateUser);
    }

    public MbtiResultResponse checkMbtiAndSave(Long userId, List<MbtiAnswer> answers) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NoSuchUserException("사용자를 찾을 수 없음");
        }

        MBTI mbtiType = MBTI.valueOf(MBTICalculator.calculate(answers));

        User updateUser = User.updateMbti(user, mbtiType);
        userRepository.update(updateUser);


        JobClass jobClass = jobClassRepository.findByCode(mbtiType.getJobClassCode());

        log.info("Job Type: {}", jobClass.getCode().getDisplayName());

        return MbtiResultResponse.builder()
                .mbti(mbtiType)
                .jobCode(jobClass.getCode())
                .jobDisplayName(jobClass.getCode().getDisplayName())
                .mbtiDescription(jobClass.getDescription())
                .build();
    }

    public MbtiResultResponse getMbti(String mbtiType) {
        MBTI mbti = MBTI.valueOf(mbtiType);
        JobClass jobClass = jobClassRepository.findByCode(mbti.getJobClassCode());

        return MbtiResultResponse.builder()
                .mbti(mbti)
                .jobCode(jobClass.getCode())
                .jobDisplayName(jobClass.getCode().getDisplayName())
                .mbtiDescription(jobClass.getDescription())
                .build();
    }

    public List<User> getUsersByIds(List<Long> allUserIds) {
        return userRepository.findAllByIds(allUserIds);
    }

    public List<UserClearDungeonResponse> getClearDungeonResponsesByUserId(Long userId){
        return userRepository.selectClearedDungeonsByUserId(userId);
    }

	public void logoutUser(String accessToken, String refreshToken) {
		Claims claims = jwtResolver.getClaims(jwtResolver.resolveToken(accessToken));
		Date expiration = claims.getExpiration();

		// 2) 지금과 만료 시각 간의 차이를 Duration으로 계산
		Instant now = Instant.now();
		Instant expInstant = expiration.toInstant();
		Duration secondsUntilExpiry = Duration.between(now, expInstant);
		if (secondsUntilExpiry.getSeconds() > 0) {
		    blackListService.addToBlackList(accessToken, secondsUntilExpiry);
		}
		if(!refreshToken.isEmpty()) {
			refreshTokenRepository.deleteByRefreshToken(refreshToken);
		}
	}

}
