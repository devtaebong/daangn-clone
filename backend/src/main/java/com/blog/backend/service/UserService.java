package com.blog.backend.service;

import com.blog.backend.entity.User;
import com.blog.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(String username, String password, String email) {
        return userRepository.save(
        User.builder()
                .username(username)
                .password(password)
                .email(email)
                .createdDate(LocalDateTime.now())
                .build()
        );
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
