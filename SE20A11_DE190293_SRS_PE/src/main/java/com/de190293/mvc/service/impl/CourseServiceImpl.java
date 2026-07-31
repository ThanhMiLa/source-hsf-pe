package com.de190293.mvc.service.impl;

import com.de190293.mvc.dto.CourseDto;
import com.de190293.mvc.repository.CourseRepository;
import com.de190293.mvc.service.CourseService;
import com.de190293.mvc.util.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<CourseDto> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(course -> GenericMapper.map(course, CourseDto.class))
                .toList();

    }
}
