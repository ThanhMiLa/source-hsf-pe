package com.de190293.mvc.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private Integer id;

    @Column(name = "tour_name", nullable = false, unique = true)
    private String tourName;
    @Column(name = "destination", nullable = false)
    private String destination;
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "status", nullable = false)
    private String status;

    public Tour() {
    }

    public Tour(Integer id, String tourName, String destination, Integer capacity, Integer duration, LocalDate startDate, double price, String status) {
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
}
