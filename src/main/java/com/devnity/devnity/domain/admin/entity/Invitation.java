package com.devnity.devnity.domain.admin.entity;

import com.devnity.devnity.domain.user.entity.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "invitations")
@AllArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, length = 30)
    private UUID uuid;

    @Column(name = "course", nullable = false, length = 20)
    private String course;

    @Column(name = "generation", nullable = false)
    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role;

    public Invitation(String course, Integer generation, UserRole role) {
        this.uuid = UUID.randomUUID();
        this.course = course;
        this.generation = generation;
        this.role = role;
    }
}
