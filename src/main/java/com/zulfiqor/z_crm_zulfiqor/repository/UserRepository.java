package com.zulfiqor.z_crm_zulfiqor.repository;

import com.zulfiqor.z_crm_zulfiqor.model.entity.Roles;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT u.* FROM users u LEFT JOIN user_roles ur on u.id = ur.user_id " +
            " LEFT JOIN roles r ON ur.role_id = r.id WHERE r.role_name IN (:roles)", nativeQuery = true)
    List<User> findAllByRoleName(List<String> roles);

    boolean existsByPhoneNumber(String phoneNumber);

}
