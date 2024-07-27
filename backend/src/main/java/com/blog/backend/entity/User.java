package com.blog.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(insertable = true)
    private LocalDateTime createdDate;

    // DB 클러스터에서 동시성 문제가 발생할 수 있기 때문에 시간은 코드레벨에서 관리
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Column
    private LocalDateTime lastLogin;
}

