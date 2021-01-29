package com.dms.pms.entity.pms.user;

import com.dms.pms.entity.pms.student.Student;
import com.dms.pms.security.auth.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor @NoArgsConstructor @Builder @Getter
public class User {
    @Id
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "StudentUser", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "student_code"))
    private Set<Student> students = new HashSet<>();

    public void setName(String name) {
        this.name = name;
    }

    public User addStudent(Student student) {
        students.add(student);
        student.getUsers().add(this);

        return this;
    }

    public User changePassword(String password) {
        this.password = password;
        return this;
    }
}
