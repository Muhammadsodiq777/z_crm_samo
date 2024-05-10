package com.zulfiqor.z_crm_zulfiqor.mapper;

import com.zulfiqor.z_crm_zulfiqor.model.dto.response.AccountDetail;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T18:38:43+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public AccountDetail toAccountDetail(User user) {
        if ( user == null ) {
            return null;
        }

        AccountDetail accountDetail = new AccountDetail();

        accountDetail.setId( user.getId() );
        accountDetail.setPhoneNumber( user.getPhoneNumber() );
        accountDetail.setFio( user.getFio() );
        accountDetail.setProfileImage( user.getProfileImage() );

        return accountDetail;
    }

    @Override
    public List<AccountDetail> toListAccount(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<AccountDetail> list = new ArrayList<AccountDetail>( users.size() );
        for ( User user : users ) {
            list.add( toAccountDetail( user ) );
        }

        return list;
    }
}
