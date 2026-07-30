package com.trial.dto;

public class ShoesDTO {

    private Integer shoesId;

    private String shoesNo;

    private String shoesName;

    private String type;

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
