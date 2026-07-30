package com.template_copy_paste.controller;

import com.template_copy_paste.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;



}
