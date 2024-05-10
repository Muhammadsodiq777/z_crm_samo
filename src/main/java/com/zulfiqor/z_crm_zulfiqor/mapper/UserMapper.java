package com.zulfiqor.z_crm_zulfiqor.mapper;

import com.zulfiqor.z_crm_zulfiqor.model.dto.response.AccountDetail;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AccountDetail toAccountDetail(User user);

    List<AccountDetail> toListAccount(List<User> users);
}
