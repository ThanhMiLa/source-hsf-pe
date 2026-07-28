package com.trial.repository;

import com.trial.entity.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Integer> {

    List<Shoes> findByShoesNameContainingIgnoreCase(String shoeName);
    Optional<Shoes> findByShoesNo(String shoesNo);
}
