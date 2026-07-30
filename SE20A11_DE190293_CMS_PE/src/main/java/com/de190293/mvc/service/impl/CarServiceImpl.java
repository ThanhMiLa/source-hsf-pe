package com.de190293.mvc.service.impl;

import com.de190293.mvc.dto.CarDto;
import com.de190293.mvc.entity.Car;
import com.de190293.mvc.repository.CarRepository;
import com.de190293.mvc.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<CarDto> findAll(String model){
        if(model == null || model.isBlank()){
            return carRepository.findAllByOrderByBrandAscModelAsc()
                    .stream()
                    .map(this::mapToCarDto)
                    .toList();
        }else{
            return carRepository.findAllByModelContainingIgnoreCaseOrderByModelAsc(model)
                    .stream()
                    .map(this::mapToCarDto)
                    .toList();
        }
    }

    @Override
    public void deleteCar(Integer id) {
        if(carRepository.findById(id).isPresent()){
            carRepository.deleteById(id);
        }
    }

    @Override
    public void addNewCar(CarDto carDto) {
        Car car = new Car();
        car.setBrand(carDto.getBrand());
        car.setColor(carDto.getColor());
        car.setModel(carDto.getModel());
        car.setPrice(carDto.getPrice());
        car.setPlate(carDto.getPlate());
        car.setYear(carDto.getYear());
        car.setStatus(carDto.getStatus());
        carRepository.save(car);
    }

    @Override
    public CarDto getCar(Integer id) {
        if(carRepository.findById(id).isPresent()){
            return carRepository.findById(id)
                    .map(this::mapToCarDto)
                    .orElseThrow(() -> new RuntimeException("Car ID: " + id + " not existed in system."));
        }
        return null;
    }

    @Override
    public boolean existedCarModel(String model) {
        return carRepository.existsByModel(model);
    }

    private CarDto mapToCarDto(Car car){
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setColor(car.getColor());
        carDto.setPlate(car.getPlate());
        carDto.setPrice(car.getPrice());
        carDto.setYear(car.getYear());
        carDto.setStatus(car.getStatus());
        return carDto;
    }


}
