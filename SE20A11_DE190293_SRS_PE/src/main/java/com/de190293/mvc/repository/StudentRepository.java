package com.de190293.mvc.repository;

import com.de190293.mvc.entity.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByOrderByFullNameAsc();
    List<Student> findAllByFullNameIgnoreCaseOrderByFullNameAsc(String fullName);
    boolean existsByStudentNo(String StudentNo);
}
