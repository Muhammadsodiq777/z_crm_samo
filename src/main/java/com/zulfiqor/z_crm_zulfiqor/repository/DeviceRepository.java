package com.zulfiqor.z_crm_zulfiqor.repository;

import com.zulfiqor.z_crm_zulfiqor.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByDeviceId(String deviceId);
    List<Device> findAllByFirebaseToken(String token);
    List<Device> findAllByUserIdIn(List<Long> userId);
}
