package com.dms.pms.entity.pms.meal;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meal_apply")
@AllArgsConstructor @NoArgsConstructor
public class MealApply {
    @Id
    private String email;

    @Column(nullable = false)
    private boolean status;

    public MealApply changeStatusToTrue() {
        this.status = true;
        return this;
    }

    public MealApply changeStatusToFalse() {
        this.status = false;
        return this;
    }

    public boolean getStatus() {
        return status;
    }
}