package com.blog.backend.service;

import com.blog.backend.dto.SignUpUser;
import com.blog.backend.entity.User;
import com.blog.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignUpUser signUpUser) {
        return userRepository.save(
        User.builder()
                .username(signUpUser.getUsername())
                .password(passwordEncoder.encode(signUpUser.getPassword()))
                .email(signUpUser.getEmail())
                .createdDate(LocalDateTime.now())
                .build()
        );
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
