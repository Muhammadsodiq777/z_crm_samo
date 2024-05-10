package com.zulfiqor.z_crm_zulfiqor.repository;

import com.zulfiqor.z_crm_zulfiqor.model.entity.Roles;
import com.zulfiqor.z_crm_zulfiqor.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByRoleName(Role role);

    List<Roles> findAllByRoleNameIn(List<Role> roles);
}
