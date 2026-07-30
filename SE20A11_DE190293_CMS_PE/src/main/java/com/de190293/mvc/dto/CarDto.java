package com.de190293.mvc.dto;


import com.de190293.mvc.annotation.*;

public class CarDto {


    private Integer id;


    @NotBlank
    @StringLength(min = 1, max = 50)
    private String model;

    @NotBlank
    @ValidStatus(allowed = {"2023", "2024", "2025", "2026"})
    private String year;

    @NotBlank
    @ValidStatus(allowed = {"Yellow", "White", "Black"})
    private String color;

    @NotBlank
    @RegexPattern(regexp = "^[A-Z]{2}\\d{1}-\\d{5}$")
    private String plate;

    @NotBlank
    @ValidStatus(allowed = {"Toyota", "Honda", "Kia", "Hyundai"})
    private String brand;

    @NotBlank
    @PriceRange(min = 1000, max = 10000)
    private Double price;

    @NotBlank
    @ValidStatus(allowed = {"AC-Active", "IN-Inactive", "DR-Draft"})
    private String status;

    public CarDto() {
    }

    public CarDto(Integer id, String model, String year, String color, String plate, String brand, Double price, String status) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.color = color;
        this.plate = plate;
        this.brand = brand;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
