package com.de190293.mvc.controller;

import com.de190293.mvc.annotation.CustomValidationEngine;
import com.de190293.mvc.dto.CarDto;
import com.de190293.mvc.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping()
    public String findAll(@RequestParam(name = "carModel", required = false) String carModel, Model model){
        List<CarDto> carDtoList = carService.findAll(carModel);
        model.addAttribute("carList", carDtoList);
        model.addAttribute("carModel", carModel);
        return "car-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes){
        carService.deleteCar(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");
        return "redirect:/cars";
    }

    @GetMapping("add")
    public String showAddForm(Model model){
        model.addAttribute("carDto", new CarDto());
        return "add-car";
    }
    @PostMapping("/add")
    public String addNewCar(@ModelAttribute(name = "carDto") CarDto carDto, Model model, RedirectAttributes redirectAttributes){
        Map<String, String> errors = CustomValidationEngine.validate(carDto);

        if(carService.existedCarModel(carDto.getModel())){
            errors.put("model", "Car Model: " + carDto.getModel() + " already exists in system");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("carDto", new CarDto());
            return "add-car";
        }

        carService.addNewCar(carDto);
        redirectAttributes.addFlashAttribute("message", "Added successfully");
        return "redirect:/cars";
    }


    @GetMapping("/view/{id}")
    public String showFormDetail(@PathVariable("id") Integer id, Model model){
        CarDto carDto = carService.getCar(id);
        model.addAttribute("carDto", carDto);
        return "view-car.html";
    }














}
