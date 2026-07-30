package com.de190293.mvc.controller;

import com.de190293.mvc.annotations.CustomValidationEngine;
import com.de190293.mvc.dto.TourDto;
import com.de190293.mvc.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping()
    public String getAllTour(@RequestParam(name = "tourName", required = false) String tourName, Model model){
        List<TourDto> tourResponseList = tourService.findAll(tourName);
        model.addAttribute("tourList", tourResponseList);
        model.addAttribute("tourName", tourName);
        return "tour-list";
    }

    @GetMapping("delete/{id}")
    public String deleteTour(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes){
        tourService.deleteTour(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");
        return "redirect:/tours";

    }

    @GetMapping("add")
    public String showAddForm(Model model){
        model.addAttribute("tourDto", new TourDto());
        return "add-tour";
    }

    @PostMapping("/add")
    public String addNewTour(@ModelAttribute(name = "tourDto") TourDto tourDto, Model model, RedirectAttributes redirectAttributes){
        Map<String, String> errors = CustomValidationEngine.validate(tourDto);

        if(tourService.existedTourName(tourDto.getTourName())){
            errors.put("tourName", "Tour Name: " + tourDto.getTourName() + " already exists in system");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("tourDto", new TourDto());
            return "add-tour";
        }

        tourService.addTour(tourDto);
        redirectAttributes.addFlashAttribute("message", "Added successfully");
        return "redirect:/tours";
    }

    @GetMapping("/view/{id}")
    public String showFormDetail(@PathVariable("id") Integer id, Model model){
        TourDto tourDto = tourService.getTour(id);
        model.addAttribute("tourDto", tourDto);
        return "view-tour.html";
    }

}
