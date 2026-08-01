package com.de190293.mvc.controller;

import com.de190293.mvc.annotation.CustomValidationEngine;
import com.de190293.mvc.dto.DeviceDto;
import com.de190293.mvc.service.CategoryService;
import com.de190293.mvc.service.DeviceSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceSerivce deviceSerivce;

    @Autowired
    private CategoryService categoryService;


    @GetMapping()
    public String getAllDevice(@RequestParam(name = "deviceName", required = false) String deviceName, Model model){
        List<DeviceDto> deviceList = deviceSerivce.findAll(deviceName);
        model.addAttribute("deviceList", deviceList);
        model.addAttribute("deviceName", deviceName);
        return "device-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteDevice(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        deviceSerivce.deleteDevice(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");
        return "redirect:/devices";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("deviceDto", new DeviceDto());
        model.addAttribute("categories", categoryService.findAll());
        return "add-device";
    }

    @PostMapping("/add")
    public String addDevice(@ModelAttribute(name = "deviceDto") DeviceDto deviceDto, RedirectAttributes redirectAttributes, Model model) {

        Map<String, String> errors = CustomValidationEngine.validate(deviceDto);

        if(deviceSerivce.existsByDeviceCode(deviceDto.getDeviceCode())){
            errors.put("deviceCode", "Device Code: " + deviceDto.getDeviceCode() + " already exists in system");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("deviceDto", new DeviceDto());
            return "add-device";
        }
        deviceSerivce.addDevice(deviceDto);
        redirectAttributes.addFlashAttribute("message", "Added successfully");
        return "redirect:/devices";
    }

    @GetMapping("/view/{id}")
    public String showFormDetail(@PathVariable("id") Integer id, Model model){
        DeviceDto deviceDto = deviceSerivce.findById(id);
        model.addAttribute("deviceDto", deviceDto);

        System.out.println("PRICE:" + deviceDto.getPrice());
        return "view-device";
    }









}
