package com.de190293.mvc.dto;


import com.de190293.mvc.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DeviceDto {

    private Integer id;

    @NotBlank
    @RegexPattern(regexp = "^[A-Z]{3}\\d{3}$")
    private String deviceCode;

    @NotBlank
    @StringLength(min = 1, max = 200)
    private String deviceName;

    @NotBlank
    @PriceRange(min = 0)
    private BigDecimal price;

    @NotBlank
    @IntRange(min = 1, max = 60)
    private Integer warrantyMonths;

    @NotBlank
    @ValidDate(futureOnly = true)
    private LocalDate installationDate;

    @NotBlank
    @ValidStatus(allowed = {"Active", "Inactive", "Draft"})
    private String status;

    @NotBlank
    private String categoryName;

    public DeviceDto() {
    }

    public DeviceDto(Integer id, String deviceCode, String deviceName, BigDecimal price, Integer warrantyMonths, LocalDate installationDate, String status, String categoryName) {
        this.id = id;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.price = price;
        this.warrantyMonths = warrantyMonths;
        this.installationDate = installationDate;
        this.status = status;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(Integer warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

