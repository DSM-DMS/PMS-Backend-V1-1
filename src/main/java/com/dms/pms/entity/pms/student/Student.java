package com.dms.pms.entity.pms.student;

import com.dms.pms.entity.pms.student_user.StudentUser;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
@Getter
public class Student {
    @Id
    private Integer studentCode;

    @Column(nullable = false)
    private String studentId;

    @OneToMany(mappedBy = "student")
    private Set<StudentUser> studentUsers = new HashSet<>();
}
