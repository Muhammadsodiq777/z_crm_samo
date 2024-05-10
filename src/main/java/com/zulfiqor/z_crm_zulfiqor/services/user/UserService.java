package com.zulfiqor.z_crm_zulfiqor.services.user;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.ReqHeader;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.*;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.AccountDetail;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.HomeDetail;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.UserResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;

import java.util.List;

public interface UserService {

    AccountDetail getUser(User currentUser);
    AccountDetail getUserAdmin(User currentUser);

    SaveResponse firebaseNotification(User currentUser, FirebaseRequest request, ReqHeader reqHeader);

    BaseResponse<?> unsubscribe(User currentUser, FirebaseRequest request, ReqHeader reqHeader);

    BaseResponse<?> giveRoleToUser(User currentUser, RoleRequest request);

    BaseResponse<?> changePassword(User currentUser, ChangePasswordRequest request);

    BaseResponse<?> blockedUser(User currentUser, AccountBlockedRequest dto);

    BaseResponse<?> unblockUser(User currentUser, AccountBlockedRequest request);

    BaseResponse<?> newEmployee(User currentUser, AddEmployeeRequest dto);

    List<AccountDetail> getUserList();

    BaseResponse<UserResponse> getUserById(Long id);

    BaseResponse<?> getRoleList();

    BaseResponse<?> removeRoleFromUser(Long userId, Long roleId);
}
