package com.dms.pms.entity.dms.stay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stay_apply")
@AllArgsConstructor @NoArgsConstructor @Getter
public class Stay {
    @Id @Column(name = "student_id", length = 20)
    private String studentId;

    @Column(name = "value", length = 11)
    private Integer value;
}
