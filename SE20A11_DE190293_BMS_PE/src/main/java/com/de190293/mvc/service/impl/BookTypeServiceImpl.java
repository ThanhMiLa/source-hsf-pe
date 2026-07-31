package com.de190293.mvc.service.impl;

import com.de190293.mvc.dto.BookTypeDto;
import com.de190293.mvc.entity.BookType;
import com.de190293.mvc.repository.BookTypeRepository;
import com.de190293.mvc.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTypeServiceImpl implements BookTypeService {
    @Autowired
    private BookTypeRepository bookTypeRepository;


    @Override
    public List<BookTypeDto> findAll() {
        return  bookTypeRepository.findAll()
                .stream()
                .map(bookType -> {
                    BookTypeDto bookTypeDto = new BookTypeDto();
                    bookTypeDto.setTypeCode(bookType.getTypeCode());
                    bookTypeDto.setTypeName(bookType.getTypeName());
                    return bookTypeDto;
                })
                .toList();
    }
}

