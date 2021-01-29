package com.dms.pms.entity.dms.point.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "point_item")
@AllArgsConstructor @NoArgsConstructor @Getter
public class Item {
    @Id @Column(name = "id", length = 11)
    private Integer id;

    @Column(name = "reason", length = 100)
    private String reason;

    @Column(name = "point", length = 11)
    private Integer point;

    @Column(name = "type")
    private boolean type;
}
