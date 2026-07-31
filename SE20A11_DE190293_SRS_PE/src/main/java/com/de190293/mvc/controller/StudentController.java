package com.de190293.mvc.controller;

import com.de190293.mvc.annotation.CustomValidationEngine;
import com.de190293.mvc.dto.CourseDto;
import com.de190293.mvc.dto.StudentDto;
import com.de190293.mvc.service.CourseService;
import com.de190293.mvc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping()
    public String getAll(@RequestParam(name = "fullName", required = false) String fullName, Model model){
        List<StudentDto> studentList = studentService.findAll(fullName);
        model.addAttribute("studentList", studentList);
        model.addAttribute("fullName", fullName);
        return "student-list";
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");
        return "redirect:/students";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        model.addAttribute("courses", courseService.findAll());

        return "add-student";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute(name = "studentDto") StudentDto studentDto, RedirectAttributes redirectAttributes, Model model) {

        Map<String, String> errors = CustomValidationEngine.validate(studentDto);

        if(studentService.existsStudentNo(studentDto.getStudentNo())){
            errors.put("studentNo", "Student No: " + studentDto.getStudentNo() + " already exists in system");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("studentDto", new StudentDto());
            return "add-student";
        }

        studentService.addStudent(studentDto);
        redirectAttributes.addFlashAttribute("message", "Added successfully");
        return "redirect:/students";
    }

    @GetMapping("/view/{id}")
    public String showFormDetail(@PathVariable("id") Integer id, Model model){
        StudentDto studentDto = studentService.findStudentById(id);
        model.addAttribute("studentDto", studentDto);
        return "view-student.html";
    }







}
