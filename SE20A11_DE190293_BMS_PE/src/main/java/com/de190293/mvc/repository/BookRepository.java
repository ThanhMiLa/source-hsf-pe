package com.de190293.mvc.repository;

import com.de190293.mvc.dto.BookDto;
import com.de190293.mvc.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByOrderByBookNameAsc();
    List<Book> findAllByBookNameContainingIgnoreCaseOrderByBookNameAsc(String bookName);
    boolean existsBooksByBookNo(String bookNo);
}
