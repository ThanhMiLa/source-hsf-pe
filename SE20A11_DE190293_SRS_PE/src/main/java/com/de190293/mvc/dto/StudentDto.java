package com.de190293.mvc.dto;


import com.de190293.mvc.annotation.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDto {

    private Integer id;

    @NotBlank
    @RegexPattern(regexp = "^SV\\d{6}$")
    private String studentNo;

    @NotBlank
    @StringLength(min = 1, max = 100)
    private String fullName;

    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank
    @VNPhone
    private String phone;

    @NotBlank
    @ValidAge(min = 16)
    private LocalDate dob;

    @NotBlank
    private List<CourseDto> courses = new ArrayList<>();

    public StudentDto() {
    }

    public StudentDto(Integer id, String studentNo, String fullName, String email, String phone, LocalDate dob, List<CourseDto> courses) {
        this.id = id;
        this.studentNo = studentNo;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.courses = courses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }
}
