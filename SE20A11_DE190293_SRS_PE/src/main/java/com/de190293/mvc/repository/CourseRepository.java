package com.de190293.mvc.repository;

import com.de190293.mvc.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    List<Course> findAllByStudentId(@Param("studentId") Integer studentId);

    List<Course> findByStudents_Id(Integer studentId);

    List<Course> findCourseByCourseNameIn(List<String> courseName);


}
