package com.dms.pms.entity.pms.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
    public Optional<Student> findByStudentNumber(Integer studentNumber);
}
