package com.de190293.mvc.dto;

public class CourseDto {
    private String courseName;

    public CourseDto() {
    }

    public CourseDto(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return this.courseName + ", ";
    }
}
