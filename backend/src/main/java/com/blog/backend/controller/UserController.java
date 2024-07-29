package com.blog.backend.controller;

import com.blog.backend.dto.SignUpUser;
import com.blog.backend.entity.User;
import com.blog.backend.jwt.JwtUtil;
import com.blog.backend.service.CustomUserDetailService;
import com.blog.backend.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CustomUserDetailService userDetailService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signUp")
    public ResponseEntity<User> createUser(@RequestBody SignUpUser signUpUser) {
        User user = userService.createUser(signUpUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user to be deleted", required = true)
                                           @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        String token = jwtUtil.generateToken(userDetails.getUsername());
        Cookie cookies = new Cookie("jwt_token", token);
        cookies.setHttpOnly(true);
        cookies.setPath("/");
        cookies.setMaxAge(60 * 60);

        response.addCookie(cookies);

        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookies = new Cookie("jwt_token", null);
        cookies.setHttpOnly(true);
        cookies.setPath("/");
        cookies.setMaxAge(0);

        response.addCookie(cookies);
    }

    @PostMapping("/token/validation")
    @ResponseStatus(HttpStatus.OK)
    public void jwtValidate(@RequestParam String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
