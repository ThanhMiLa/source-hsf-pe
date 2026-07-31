package com.de190293.mvc.service;

import com.de190293.mvc.dto.BookTypeDto;
import com.de190293.mvc.entity.BookType;

import java.util.List;

public interface BookTypeService {
    List<BookTypeDto> findAll();
}
