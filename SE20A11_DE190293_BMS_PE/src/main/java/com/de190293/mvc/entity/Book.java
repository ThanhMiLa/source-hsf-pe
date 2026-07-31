package com.de190293.mvc.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer id;

    @Column(name ="book_no", nullable = false, unique = true)
    private String bookNo;

    @Column(name ="book_name", nullable = false)
    private String bookName;

    @Column(name ="isbn", nullable = false)
    private String isbn;

    @Column(name ="author_email", nullable = false)
    private String authorEmail;

    @Column(name ="publisher_phone", nullable = false)
    private String publisherPhone;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", nullable = false)
    private BookType bookType;

    public Book() {
    }

    public Book(Integer id, String bookNo, String bookName, String isbn, String authorEmail, String publisherPhone, LocalDate publishDate, Double price, BookType bookType) {
        this.id = id;
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.isbn = isbn;
        this.authorEmail = authorEmail;
        this.publisherPhone = publisherPhone;
        this.publishDate = publishDate;
        this.price = price;
        this.bookType = bookType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
}
