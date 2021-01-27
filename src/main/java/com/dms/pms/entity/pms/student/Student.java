package com.dms.pms.entity.pms.student;

import com.dms.pms.entity.pms.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
@Getter @NoArgsConstructor @AllArgsConstructor
public class Student {
    @Id @Column(name = "student_code")
    private Integer studentCode;

    @Column(nullable = false)
    private String studentId;

    @ManyToMany(mappedBy = "students")
    private Set<User> users = new HashSet<>();

    // For testing
    public Student(Integer studentCode, String studentId) {
        this.studentCode = studentCode;
        this.studentId = studentId;
    }
}