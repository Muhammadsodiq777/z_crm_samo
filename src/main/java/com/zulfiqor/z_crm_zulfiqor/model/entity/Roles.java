package com.zulfiqor.z_crm_zulfiqor.model.entity;

import com.zulfiqor.z_crm_zulfiqor.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role roleName;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

}
