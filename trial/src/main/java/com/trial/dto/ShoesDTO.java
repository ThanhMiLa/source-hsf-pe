package com.trial.dto;

import com.trial.annotation.AlphaNumeric;
import com.trial.annotation.NotBlank;
import com.trial.annotation.PriceRange;
import com.trial.annotation.StringLength;

public class ShoesDTO {

    private Integer shoesId;

    @NotBlank
    @StringLength(min = 10, max = 10)
    @AlphaNumeric
    private String shoesNo;

    @NotBlank
    @StringLength(min = 1, max = 100)
    private String shoesName;

    @NotBlank
    private String type;

    @PriceRange(min = 0, max = 100000)
    private double price;

    public ShoesDTO() {
    }

    public ShoesDTO(Integer shoesId, String shoesNo, String shoesName, String type, double price) {
        this.shoesId = shoesId;
        this.shoesNo = shoesNo;
        this.shoesName = shoesName;
        this.type = type;
        this.price = price;
    }

    public Integer getShoesId() {
        return shoesId;
    }

    public void setShoesId(Integer shoesId) {
        this.shoesId = shoesId;
    }

    public String getShoesNo() {
        return shoesNo;
    }

    public void setShoesNo(String shoesNo) {
        this.shoesNo = shoesNo;
    }

    public String getShoesName() {
        return shoesName;
    }

    public void setShoesName(String shoesName) {
        this.shoesName = shoesName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShoesDTO{" +
                "shoesId=" + shoesId +
                ", shoesNo='" + shoesNo + '\'' +
                ", shoesName='" + shoesName + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
