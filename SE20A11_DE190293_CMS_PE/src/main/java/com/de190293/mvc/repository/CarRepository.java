package com.de190293.mvc.repository;

import com.de190293.mvc.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByOrderByBrandAscModelAsc();
    List<Car> findAllByModelContainingIgnoreCaseOrderByModelAsc(String model);
    boolean existsByModel(String model);
}
