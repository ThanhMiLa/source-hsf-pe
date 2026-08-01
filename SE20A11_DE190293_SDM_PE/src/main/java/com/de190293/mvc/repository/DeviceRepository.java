package com.de190293.mvc.repository;

import com.de190293.mvc.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findAllByOrderByInstallationDateDesc();
    List<Device> findAllByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(String deviceName);
    boolean existsByDeviceCode(String deviceCode);

}
