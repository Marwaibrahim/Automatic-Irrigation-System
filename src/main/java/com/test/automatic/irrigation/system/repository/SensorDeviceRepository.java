package com.test.automatic.irrigation.system.repository;

import com.test.automatic.irrigation.system.entity.SensorDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDeviceRepository extends JpaRepository <SensorDevice, Long>{

    SensorDevice findByDeviceId (String device_id);
}
