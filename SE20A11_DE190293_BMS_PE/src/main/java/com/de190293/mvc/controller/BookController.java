package com.de190293.mvc.controller;

import com.de190293.mvc.annotation.CustomValidationEngine;
import com.de190293.mvc.dto.BookDto;
import com.de190293.mvc.repository.BookTypeRepository;
import com.de190293.mvc.service.BookService;
import com.de190293.mvc.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookTypeService bookTypeService;

    @GetMapping()
    public String findAllBooks(@RequestParam(name = "bookName", required = false) String bookName, Model model) {
        List<BookDto> bookDtoList = bookService.findAll(bookName);
        model.addAttribute("bookList", bookDtoList);
        model.addAttribute("bookName", bookName);
        return "book-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteShoes(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");
        return "redirect:/books";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("bookDto", new BookDto());
        model.addAttribute("types", bookTypeService.findAll());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute(name = "bookDto") BookDto bookDto, RedirectAttributes redirectAttributes, Model model) {

        Map<String, String> errors = CustomValidationEngine.validate(bookDto);

        if(bookService.existedByBookNo(bookDto.getBookNo())){
            errors.put("bookNo", "Book No: " + bookDto.getBookNo() + " already exists in system");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("types", bookTypeService.findAll());
            model.addAttribute("bookDto", new BookDto());
            return "add-book";
        }

        bookService.addBook(bookDto);
        redirectAttributes.addFlashAttribute("message", "Added successfully");
        return "redirect:/books";
    }

    @GetMapping("/view/{id}")
    public String showFormDetail(@PathVariable("id") Integer id, Model model){
        BookDto bookDto = bookService.findById(id);
        model.addAttribute("bookDto", bookDto);
        return "view-book.html";
    }







}
