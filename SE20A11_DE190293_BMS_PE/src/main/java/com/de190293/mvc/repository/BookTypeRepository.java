package com.de190293.mvc.repository;

import com.de190293.mvc.entity.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, Integer> {
    BookType findByTypeName(String typeName);
}
