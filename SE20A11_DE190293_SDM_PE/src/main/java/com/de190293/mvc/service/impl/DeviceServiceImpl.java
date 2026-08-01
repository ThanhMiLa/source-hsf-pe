package com.de190293.mvc.service.impl;

import com.de190293.mvc.dto.DeviceDto;
import com.de190293.mvc.entity.Device;
import com.de190293.mvc.repository.CategoryRepository;
import com.de190293.mvc.repository.DeviceRepository;
import com.de190293.mvc.service.DeviceSerivce;
import com.de190293.mvc.util.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeviceServiceImpl implements DeviceSerivce {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<DeviceDto> findAll(String deviceName) {
        if(deviceName == null || deviceName.isBlank()){
            return deviceRepository.findAllByOrderByInstallationDateDesc()
                    .stream()
                    .map(this::mapToDto)
                    .toList();
        }else{
            return deviceRepository.findAllByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(deviceName)
                    .stream()
                    .map(this::mapToDto)
                    .toList();
        }
    }

    @Override
    public void deleteDevice(Integer id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public DeviceDto findById(Integer id) {
        return deviceRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Device not found"));
    }

    @Override
    public boolean existsByDeviceCode(String deviceCode) {
        return  deviceRepository.existsByDeviceCode(deviceCode);
    }

    @Override
    public void addDevice(DeviceDto deviceDto) {
        Device device = GenericMapper.map(deviceDto, Device.class);
        device.setPrice(deviceDto.getPrice());
        device.setCategory(categoryRepository.findByCategoryName(deviceDto.getCategoryName()));
        deviceRepository.save(device);
    }

    private DeviceDto mapToDto(Device device) {
        DeviceDto deviceDto = GenericMapper.map(device, DeviceDto.class);
        deviceDto.setPrice(device.getPrice());
        deviceDto.setCategoryName(device.getCategory().getCategoryName());
        return deviceDto;
    }
}
