package com.de190293.mvc.dto;

import com.de190293.mvc.annotation.*;

import java.time.LocalDate;

public class BookDto {

    private Integer id;

    @NotBlank
    @RegexPattern(regexp = "^BK\\d{6}$")
    private String bookNo;

    @NotBlank
    @StringLength(min = 1, max = 150)
    private String bookName;

    @NotBlank
    @RegexPattern(regexp =  "^(?=(?:\\D*\\d){13}$)(97[89])[- ]?\\d{1,5}[- ]?\\d+[- ]?\\d+[- ]?\\d$")
    private String isbn;

    @NotBlank
    @ValidEmail
    private String authorEmail;

    @NotBlank
    @VNPhone
    private String publisherPhone;

    @NotBlank
    @ValidDate(pastOnly = true)
    private LocalDate publishDate;

    @NotBlank
    @PriceRange(min = 0, max = 1000000)
    private Double price;

    @NotBlank
    private String bookType;

    public BookDto() {
    }

    public BookDto(Integer id, String bookNo, String bookName, String isbn, String authorEmail, String publisherPhone, LocalDate publishDate, Double price, String bookType) {
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

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }
}
