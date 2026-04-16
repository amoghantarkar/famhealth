package com.famhealth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String status;
}
