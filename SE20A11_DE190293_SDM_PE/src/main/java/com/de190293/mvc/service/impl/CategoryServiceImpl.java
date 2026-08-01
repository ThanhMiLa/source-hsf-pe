package com.de190293.mvc.service.impl;

import com.de190293.mvc.dto.CategoryDto;
import com.de190293.mvc.repository.CategoryRepository;
import com.de190293.mvc.service.CategoryService;
import com.de190293.mvc.util.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> GenericMapper.map(category, CategoryDto.class))
                .toList();
    }
}
