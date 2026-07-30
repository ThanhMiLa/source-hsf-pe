package com.template_copy_paste.service.impl;

import com.template_copy_paste.repository.TemplateRepository;
import com.template_copy_paste.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateRepository templateRepository;


}
