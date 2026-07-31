package com.de190293.mvc.service;


import com.de190293.mvc.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAll(String bookName);
    void deleteBook(Integer id);
    void addBook(BookDto bookDto);
    boolean existedByBookNo(String bookNo);
    BookDto findById(Integer id);

}
