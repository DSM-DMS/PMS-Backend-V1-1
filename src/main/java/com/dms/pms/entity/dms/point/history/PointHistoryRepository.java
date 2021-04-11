package com.dms.pms.entity.dms.point.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer> {
    public List<PointHistory> findAllByStudentId(String studentId);
}
