package com.dms.pms.entity.pms.outing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "outing")
@AllArgsConstructor @NoArgsConstructor @Builder @Getter
public class Outing {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_number")
    private Integer studentNumber;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OutingType type;

    @Column(name = "reason")
    private String reason;

    @Column(name = "place")
    private String place;
}
