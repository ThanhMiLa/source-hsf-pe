package com.de190293.mvc.service.impl;
import com.de190293.mvc.dto.CourseDto;
import com.de190293.mvc.dto.StudentDto;
import com.de190293.mvc.entity.Course;
import com.de190293.mvc.entity.Student;
import com.de190293.mvc.repository.CourseRepository;
import com.de190293.mvc.repository.StudentRepository;
import com.de190293.mvc.service.StudentService;
import com.de190293.mvc.util.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public List<StudentDto> findAll(String fullName) {
        if(fullName == null || fullName.isBlank()){
            return studentRepository.findAllByOrderByFullNameAsc()
                    .stream()
                    .map(this::mapToStudentDto)
                    .toList();
        }else{
            return studentRepository.findAllByFullNameIgnoreCaseOrderByFullNameAsc(fullName)
                    .stream()
                    .map(this::mapToStudentDto)
                    .toList();
        }
    }

    @Override
    public void deleteStudent(Integer id) {
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
        }
    }

    @Override
    public void addStudent(StudentDto studentDto) {
        Student student = GenericMapper.map(studentDto, Student.class);
        List<String> courseName = new ArrayList<>();

        for (CourseDto courseDto : studentDto.getCourses()) {
            courseName.add(courseDto.getCourseName());
        }

        List<Course> courseList = courseRepository.findCourseByCourseNameIn(courseName);

        student.setCourses(courseList);

        studentRepository.save(student);
    }

    @Override
    public StudentDto findStudentById(Integer id) {
        return studentRepository.findById(id)
                .map(this::mapToStudentDto)
                .orElseThrow(() -> new RuntimeException("Student ID: " + id + " not existed in system"));
    }

    @Override
    public boolean existsStudentNo(String studentNo) {
        return studentRepository.existsByStudentNo(studentNo);
    }

    private StudentDto mapToStudentDto(Student student){
        StudentDto studentDto = GenericMapper.map(student, StudentDto.class);

        List<CourseDto> courseDtoList = courseRepository.findAllByStudentId(studentDto.getId())
                .stream()
                .map(course -> {
                    CourseDto courseDto = GenericMapper.map(course, CourseDto.class);
                    return courseDto;
                })
                .toList();

        studentDto.setCourses(courseDtoList);

        return studentDto;

    }
}
