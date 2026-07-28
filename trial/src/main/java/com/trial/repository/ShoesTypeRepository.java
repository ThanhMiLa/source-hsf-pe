package com.trial.repository;

import com.trial.entity.ShoesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoesTypeRepository extends JpaRepository<ShoesType, String> {
    ShoesType findByTypeName(String typeName);
    Optional<ShoesType> findByTypeCode(String typeCode);
}
