package com.de190293.mvc.service;


import com.de190293.mvc.dto.DeviceDto;
import com.de190293.mvc.entity.Device;

import java.util.List;

public interface DeviceSerivce {
    List<DeviceDto> findAll(String deviceName);
    void deleteDevice(Integer id);
    DeviceDto findById(Integer id);
    boolean existsByDeviceCode(String deviceCode);
    void addDevice(DeviceDto deviceDto);
}
