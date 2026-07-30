package com.de190293.mvc.service;


import com.de190293.mvc.dto.CarDto;

import java.util.List;

public interface CarService {
    List<CarDto> findAll(String model);
    void deleteCar(Integer id);
    void addNewCar(CarDto carDto);
    CarDto getCar(Integer id);
    boolean existedCarModel(String model);

}
