package com.dms.pms.entity.dms.point.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "point_history")
@AllArgsConstructor @NoArgsConstructor @Getter
public class PointHistory {
    @Id @Column(name = "id", length = 11)
    private Integer id;

    @Column(name = "student_id", length = 20)
    private String studentId;

    @Column(name = "point_id", length = 11)
    private Integer pointId;

    @Column(name = "point_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
