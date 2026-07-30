package com.template_copy_paste.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "template_table")
public class TemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "template_string", nullable = false)
    private String templateString;

    @Column(name = "template_integer", nullable = false)
    private Integer templateInteger;

    @Column(name = "template_double", nullable = false)
    private Double templateDouble;

    @Column(name = "template_local_date", nullable = false)
    private LocalDate templateLocalDate;

    @Column(name = "template_status")
    private String templateStatus;


}
