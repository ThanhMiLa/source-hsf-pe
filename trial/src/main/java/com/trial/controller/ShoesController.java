package com.trial.controller;

import com.trial.annotation.CustomValidationEngine;
import com.trial.dto.ShoesDTO;
import com.trial.service.ShoesService;
import com.trial.service.ShoesTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shoes")
public class ShoesController {

    private final ShoesService shoesService;
    private final ShoesTypeService shoesTypeService;


    public ShoesController(ShoesService shoesService, ShoesTypeService shoesTypeService) {
        this.shoesService = shoesService;
        this.shoesTypeService = shoesTypeService;
    }

    @GetMapping()
    public String getAllShoes(@RequestParam(name = "shoesName", required = false) String shoesName, Model model){
        List<ShoesDTO> shoesDTOS = shoesService.findAllShoes(shoesName);
        model.addAttribute("shoesList", shoesDTOS);
        model.addAttribute("shoesName", shoesName);
        return "shoes-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteShoes(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        shoesService.deleteShoes(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");
        return "redirect:/shoes";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("shoesDto", new ShoesDTO());
        model.addAttribute("types", shoesTypeService.findAllShoesType());
        return "add-shoes";
    }

    @PostMapping("/add")
    public String addShoes(@ModelAttribute(name = "shoesDto") ShoesDTO shoesDto, RedirectAttributes redirectAttributes, Model model) {

        Map<String, String> errors = CustomValidationEngine.validate(shoesDto);

        if(shoesService.existedShoesNo(shoesDto.getShoesNo())){
            errors.put("shoesNo", "Shoes No: " + shoesDto.getShoesNo() + " already exists in system");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("types", shoesTypeService.findAllShoesType());
            model.addAttribute("shoesDto", new ShoesDTO());
            return "add-shoes";
        }

        shoesService.addShoes(shoesDto);
        redirectAttributes.addFlashAttribute("message", "Added successfully");
        return "redirect:/shoes";
    }

    @GetMapping("/view/{id}")
    public String showFormDetail(@PathVariable("id") Integer id, Model model){
        ShoesDTO shoesDTO = shoesService.getShoes(id);
        model.addAttribute("shoesDto", shoesDTO);
        return "view-shoes.html";
    }
}


