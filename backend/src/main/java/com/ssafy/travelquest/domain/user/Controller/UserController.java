package com.ssafy.travelquest.domain.user.Controller;

import com.ssafy.travelquest.domain.user.dto.MbtiAnswer;
import com.ssafy.travelquest.domain.user.dto.MbtiResultResponse;
import com.ssafy.travelquest.domain.user.dto.UserClearDungeonResponse;
import com.ssafy.travelquest.domain.user.dto.UserProfileEditRequest;
import com.ssafy.travelquest.domain.user.dto.UserProfileResponse;
import com.ssafy.travelquest.domain.user.entity.User;
import com.ssafy.travelquest.domain.user.exception.NoSuchUserException;
import com.ssafy.travelquest.domain.user.service.UserService;
import com.ssafy.travelquest.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> registUser(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserProfileEditRequest userProfileEditRequest) {
        userService.editUserProfile(userDetails.getId(), userProfileEditRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mbti")
    public ResponseEntity<MbtiResultResponse> registMbti(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody List<MbtiAnswer> answers) {
        return ResponseEntity.ok(
                userService.checkMbtiAndSave(userDetails.getId(), answers)
        );
    }

    @GetMapping("/mbti/{mbtiType}")
    public ResponseEntity<MbtiResultResponse> getMbti(@PathVariable String mbtiType) {
        return ResponseEntity.ok(userService.getMbti(mbtiType));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NoSuchUserException("User not found");
        }

        return ResponseEntity.ok(
                UserProfileResponse.of(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getMbti(),
                        user.getJobClassCode(),
                        user.getRole(),
                        user.getRegistStatus(),
                        user.getBirthday(),
                        user.getCreatedAt()
                )
        );
    }

    @GetMapping("/{userId}/clear/dungeon")
    public ResponseEntity<List<UserClearDungeonResponse>> getMethodName(@PathVariable Long userId) {
        return ResponseEntity.ok(
            userService.getClearDungeonResponsesByUserId(userId)
        );
    }
    
}
