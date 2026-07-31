package com.de190293.mvc.service;


import com.de190293.mvc.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> findAll(String fullName);
    void deleteStudent(Integer id);
    boolean  existsStudentNo(String studentNo);
    void addStudent(StudentDto studentDto);
    StudentDto findStudentById(Integer id);

}
