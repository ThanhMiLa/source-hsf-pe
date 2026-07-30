package com.de190293.mvc.dto;

import com.de190293.mvc.annotations.*;

import java.time.LocalDate;

public class TourDto {
    private Integer id;

    @NotBlank
    @StringLength(min = 1, max = 200)
    private String tourName;

    @NotBlank
    @StringLength(min = 1, max = 200)
    private String destination;

    @NotBlank
    @IntRange(min = 1, max = 1000)
    private Integer capacity;

    @NotBlank
    @IntRange(min = 1, max = 360)
    private Integer duration;

    @NotBlank
    @ValidDate(futureOnly = true, maxDaysFromToday = 300)
    private LocalDate startDate;

    @NotBlank
    @PriceRange(min = 1, max = 10000)
    private double price;

    @NotBlank
    @ValidStatus(allowed = {"AC-Active", "IN-Inactive", "DR-Draft"})
    private String status;


    public TourDto() {
    }

    public TourDto(Integer id, String tourName, String destination, Integer capacity, Integer duration, LocalDate startDate, double price, String status) {
        this.id = id;
        this.tourName = tourName;
        this.destination = destination;
        this.capacity = capacity;
        this.duration = duration;
        this.startDate = startDate;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TourDto{" +
                "id=" + id +
                ", tourName='" + tourName + '\'' +
                ", destination='" + destination + '\'' +
                ", capacity=" + capacity +
                ", duration=" + duration +
                ", startDate=" + startDate +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
