package com.zulfiqor.z_crm_zulfiqor.model.enums;

import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import lombok.Getter;

@Getter
public enum Role {
    BOSS(1),
    ACCOUNTANT_CHIEF(2),
    ACCOUNTANT(3),
    MANAGER_CHIEF(4),
    MANAGER(5),
    WAREHOUSE_CHIEF(6),
    WAREHOUSE(7),
    CARRIER(8),
    NO_ROLE(9);


    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Role getById(int id) {
        for (Role r: Role.values()){
            if(r.getId() == id){
                return r;
            }
        }
        throw new NotFoundException("Role Not Found");
    }

    public static Role getRole(String roleName) {
        for (Role r: Role.values()){
            if(roleName.equals(r.name())){
                return r;
            }
        }
        return null;
    }
}
