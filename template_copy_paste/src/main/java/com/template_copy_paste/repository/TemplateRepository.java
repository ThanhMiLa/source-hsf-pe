package com.template_copy_paste.repository;

import com.template_copy_paste.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Integer> {

}
