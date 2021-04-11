package com.dms.pms.entity.pms.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealApplyRepository extends JpaRepository<MealApply, String> {
}
