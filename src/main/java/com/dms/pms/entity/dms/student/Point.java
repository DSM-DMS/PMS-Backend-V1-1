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
    @Id
    private String studentId;

    @Column(length = 11)
    private Integer goodPoint;

    @Column(length = 11)
    private Integer badPoint;

    @Column(length = 11)
    private Integer penaltyLevel;

    @Column
    private boolean penaltyStatus;
}
