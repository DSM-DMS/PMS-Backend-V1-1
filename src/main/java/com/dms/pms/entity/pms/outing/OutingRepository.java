package com.dms.pms.entity.pms.outing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutingRepository extends JpaRepository<Outing, Integer> {
    public List<Outing> findAllByStudentNumber(Integer studentNumber);
}
