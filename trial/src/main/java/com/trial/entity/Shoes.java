package com.trial.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shoes")
public class Shoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoes_id")
    private Integer id;

    @Column(name = "shoes_no")
    private String shoesNo;

    @Column(name = "shoes_name")
    private String shoesName;

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", nullable = false)
    private ShoesType shoesType;

    public Shoes() {
    }

    public Shoes(Integer id) {
        this.id = id;
    }

    public Shoes(Integer id, String shoesNo, String shoesName, double price, ShoesType shoesType) {
        this.id = id;
        this.shoesNo = shoesNo;
        this.shoesName = shoesName;
        this.price = price;
        this.shoesType = shoesType;
    }

    public Integer getId() {
        return id;
    }

    public String getShoesNo() {
        return shoesNo;
    }

    public String getShoesName() {
        return shoesName;
    }

    public double getPrice() {
        return price;
    }

    public ShoesType getShoesType() {
        return shoesType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShoesNo(String shoesNo) {
        this.shoesNo = shoesNo;
    }

    public void setShoesName(String shoesName) {
        this.shoesName = shoesName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setShoesType(ShoesType shoesType) {
        this.shoesType = shoesType;
    }
}
