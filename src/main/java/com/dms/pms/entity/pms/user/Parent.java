package com.dms.pms.entity.pms.user;

import com.dms.pms.entity.pms.student_user.StudentUser;
import com.dms.pms.security.auth.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parent")
@AllArgsConstructor @NoArgsConstructor @Builder @Getter
public class Parent {
    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private Set<StudentUser> studentUsers = new HashSet<>();
}
