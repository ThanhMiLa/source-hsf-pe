package com.template_copy_paste.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "template_table")
public class TemplateType {
    @Column(name = "template_string", nullable = false)
    private String templateString;

    @Column(name = "template_string", nullable = false)
    private String templateString;

    @OneToMany(mappedBy = "templateType", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Template> templateList = new ArrayList<>();
}
