package com.de190293.mvc.service.impl;


import com.de190293.mvc.dto.BookDto;
import com.de190293.mvc.entity.Book;
import com.de190293.mvc.repository.BookRepository;
import com.de190293.mvc.repository.BookTypeRepository;
import com.de190293.mvc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookTypeRepository bookTypeRepository;


    @Override
    public List<BookDto> findAll(String bookName) {
        if(bookName == null || bookName.isBlank()){
            return bookRepository.findAllByOrderByBookNameAsc()
                    .stream()
                    .map(this::getBookDto)
                    .toList();
        }else{
            return bookRepository.findAllByBookNameContainingIgnoreCaseOrderByBookNameAsc(bookName)
                    .stream()
                    .map(this::getBookDto)
                    .toList();
        }
    }

    @Override
    public void deleteBook(Integer id) {
        if(bookRepository.findById(id).isPresent()){
            bookRepository.deleteById(id);
        }
    }

    @Override
    public void addBook(BookDto bookDto) {
        Book book = new Book();
        book.setBookName(bookDto.getBookName());
        book.setBookNo(bookDto.getBookNo());
        book.setAuthorEmail(bookDto.getAuthorEmail());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());
        book.setPublishDate(bookDto.getPublishDate());
        book.setPublisherPhone(bookDto.getPublisherPhone());
        book.setBookType(bookTypeRepository.findByTypeName(bookDto.getBookType()));
        bookRepository.save(book);
    }

    @Override
    public boolean existedByBookNo(String bookNo) {
        return bookRepository.existsBooksByBookNo(bookNo);
    }

    @Override
    public BookDto findById(Integer id) {
        return bookRepository.findById(id)
                .map(this::getBookDto)
                .orElseThrow(() -> new RuntimeException("Book ID: " + id + " not existed in system"));
    }

    private BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setBookName(book.getBookName());
        bookDto.setBookNo(book.getBookNo());
        bookDto.setAuthorEmail(book.getAuthorEmail());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setPublishDate(book.getPublishDate());
        bookDto.setPublisherPhone(book.getPublisherPhone());
        bookDto.setBookType(book.getBookType().getTypeName());
        return bookDto;
    }
}
