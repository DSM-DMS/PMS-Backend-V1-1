package com.dms.pms.entity.dms.student;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "point_status")
@Getter
public class Point {
    @Id @Column(name = "student_id")
    private String studentId;

    @Column(length = 11, name = "good_point")
    private Integer goodPoint;

    @Column(length = 11, name = "bad_point")
    private Integer badPoint;

    @Column(length = 11, name = "penalty_level")
    private Integer penaltyLevel;

    @Column(name = "penalty_status")
    private boolean penaltyStatus;
}
