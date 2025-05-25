package com.ssafy.travelquest.global.security;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.travelquest.domain.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        var user = userService.getUser(Long.parseLong(userId));
        return new CustomUserDetails(
                user.getId(),
                user.getUserName(),          // 또는 사용자 이름
                "",                       // 비밀번호를 쓰지 않는다면 빈 문자열
                user.getJobClassCode(),
                user.getProfileImage(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
