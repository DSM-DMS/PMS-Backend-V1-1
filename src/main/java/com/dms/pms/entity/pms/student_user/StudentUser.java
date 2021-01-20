package com.dms.pms.entity.pms.student_user;

import com.dms.pms.entity.pms.student.Student;
import com.dms.pms.entity.pms.user.Parent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student_user")
@Getter @Setter
public class StudentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;
}
