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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;


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
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletRequest request,
        @CookieValue(value = "refreshToken", required = false) String refreshToken,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
    	
    	String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    	
        if (accessToken == null || refreshToken == null) {
            // 쿠키가 없으면 401 혹은 400으로 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userService.logoutUser(accessToken, refreshToken);

        // 클라이언트 쿠키 만료용 Set-Cookie 헤더
        ResponseCookie clearAccess = ResponseCookie.from("accessToken", "")
            .path("/")
            .httpOnly(true)
            .maxAge(0)
            .build();
        ResponseCookie clearRefresh = ResponseCookie.from("refreshToken", "")
            .path("/")
            .httpOnly(true)
            .maxAge(0)
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, clearAccess.toString(), clearRefresh.toString())
            .build();
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
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable Long userId, HttpServletRequest request) {
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
                        user.getProfileImage(),
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

    @PutMapping("/{userId}")
    public ResponseEntity<Void> editUserProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileEditRequest userProfileEditRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (!Objects.equals(userDetails.getId(), userId)) {
            return ResponseEntity.status(403).build();
        }
        userService.editUserProfile(userId, userProfileEditRequest);
        return ResponseEntity.ok().build();
    }
}
