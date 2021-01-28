package com.dms.pms.entity.pms.outing;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "outing")
@AllArgsConstructor @NoArgsConstructor
public class Outing {
    @Id @Column(name = "student_id")
    private String studentId;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "reason")
    private String reason;

    @Column(name = "place")
    private String place;
}
