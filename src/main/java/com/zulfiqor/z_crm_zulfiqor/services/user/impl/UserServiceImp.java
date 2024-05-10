package com.zulfiqor.z_crm_zulfiqor.services.user.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.BadRequestException;
import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import com.zulfiqor.z_crm_zulfiqor.mapper.UserMapper;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.ReqHeader;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.*;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.*;
import com.zulfiqor.z_crm_zulfiqor.model.entity.Device;
import com.zulfiqor.z_crm_zulfiqor.model.entity.Roles;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.enums.Role;
import com.zulfiqor.z_crm_zulfiqor.model.enums.UserStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.constants.ServiceList;
import com.zulfiqor.z_crm_zulfiqor.repository.DeviceRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.RoleRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.UserRepository;
import com.zulfiqor.z_crm_zulfiqor.services.notification.NotificationService;
import com.zulfiqor.z_crm_zulfiqor.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final NotificationService notificationService;
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountDetail getUser(User currentUser) {
        AccountDetail accountDetail = userMapper.toAccountDetail(currentUser);
        List<AppServicesDto> services = getUserServices(currentUser);
        accountDetail.setServices(services);
//        Set<Roles> roles = viewRoles(currentUser);
        return accountDetail;
    }

    @Override
    public AccountDetail getUserAdmin(User currentUser) {
        AccountDetail accountDetail = userMapper.toAccountDetail(currentUser);
        return accountDetail;
    }

    @Override
    public SaveResponse firebaseNotification(User currentUser, FirebaseRequest request, ReqHeader reqHeader) {
        if (reqHeader.getDeviceId() == null || reqHeader.getDeviceId().equals(""))
            throw new BadRequestException("Iltimos DeviceIdni ham qo'shib yuboring");

        Device device = deviceRepository.findByDeviceId(reqHeader.getDeviceId());
        if (device == null) {
            device = new Device();
            device.setDeviceId(reqHeader.getDeviceId());
            device.setUserId(currentUser.getId());
            device.setUserAgent(reqHeader.getDeviceId());
        }
        device.setFirebaseToken(request.getFirebaseToken());
        deviceRepository.save(device);
        notificationService.subscribeToken(currentUser, request.getFirebaseToken(), reqHeader.getLang());
        return new SaveResponse(device.getId());
    }

    @Override
    public BaseResponse<?> unsubscribe(User currentUser, FirebaseRequest request, ReqHeader reqHeader) {
        List<Device> devices = deviceRepository.findAllByFirebaseToken(request.getFirebaseToken());
        if (devices.isEmpty()) {
            throw new BadRequestException("Notogri token berdingiz");
        }
        notificationService.unsubscribe(request, reqHeader);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse<?> giveRoleToUser(User currentUser, RoleRequest request) {
        Roles role = roleRepository.findByRoleName(Role.getRole(request.getRole()));
        if (!checkUserRole(currentUser.getRoles().stream().toList(), role))
            throw new UsernameNotFoundException("Siz ushbu userga bu role bera olmaysiz");

        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (optionalUser.isEmpty())
            throw new NotFoundException("User topilmadi");

        User user = optionalUser.get();
        List<Roles> userRoles = user.getRoles();
        userRoles.add(role);
        userRepository.save(user);
        return BaseResponse.success("Role muvafaqqiyatli berildi");
    }

    @Override
    public BaseResponse<?> changePassword(User currentUser, ChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword()))
            throw new IllegalArgumentException("Password is not correct");

        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new IllegalArgumentException("Password is not same");

        currentUser.setPassword(request.getNewPassword());
        userRepository.save(currentUser);
        return BaseResponse.success("Password success changed");
    }

    @Override
    public BaseResponse<?> blockedUser(User currentUser, AccountBlockedRequest dto) {
        Optional<User> optionalUser = userRepository.findById(dto.userId());
        if (optionalUser.isEmpty())
            throw new NotFoundException("User topilmadi");
        User blockUser = optionalUser.get();

        blockUser.setStatus(UserStatus.BLOCK);
        userRepository.save(blockUser);
        return BaseResponse.success();
    }

    // TODO: 12/19/2023 merge block and unblock api into one
    @Override
    public BaseResponse<?> unblockUser(User currentUser, AccountBlockedRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.userId());
        if (optionalUser.isEmpty())
            throw new NotFoundException("User topilmadi");
        User blockUser = optionalUser.get();
        blockUser.setStatus(UserStatus.ACTIVE);
        userRepository.save(blockUser);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse<?> newEmployee(User currentUser, AddEmployeeRequest dto) {
        Roles roles = roleRepository.findByRoleName(Role.getRole(dto.role()));
        if (roles == null)
            throw new BadRequestException("Bunday role bizda mavjud emas");
        if (!checkUserRole(currentUser.getRoles().stream().toList(), roles))
            throw new UsernameNotFoundException("Siz ushbu userga bu role bera olmaysiz");
        if(userRepository.existsByPhoneNumber(dto.phoneNumber())) {
            throw new BadRequestException("Ushbu telefon raqamida user mavjud.");
        }
        User newUser = new User();
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setFio(dto.fio());
        newUser.setPhoneNumber(dto.phoneNumber());
        newUser.setRoles(List.of(roles));
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(newUser);
        return BaseResponse.success();
    }

    @Override
    public List<AccountDetail> getUserList() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            return new ArrayList<>();
        return userMapper.toListAccount(users);
    }

    @Override
    public BaseResponse<UserResponse> getUserById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty())
            return BaseResponse.error(NOT_FOUND.getCode(), "Foydalanuvchi topilmadi");
        User user = byId.get();
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFio(user.getFio());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setStatus(user.getStatus() != UserStatus.BLOCK);
        response.setImageUrl("");
        response.setRoles(user.getRoles());
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<?> getRoleList() {
        List<Roles> all = roleRepository.findAll();
        if (all.isEmpty())
            return BaseResponse.error(NOT_FOUND.getCode(), "Foydalanuvchi topilmadi");
        all.removeIf(r -> r.getRoleName() == Role.BOSS);
        return BaseResponse.success(all);
    }

    @Override
    public BaseResponse<?> removeRoleFromUser(Long userId, Long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            return BaseResponse.error(NOT_FOUND.getCode(), "Foydalanuvchi topilmadi");

        Optional<Roles> optionalRoles = roleRepository.findById(roleId);
        if (optionalRoles.isEmpty())
            return BaseResponse.error(NOT_FOUND.getCode(), "Foydalanuvchida rolelar topilmadi");

        User user = optionalUser.get();
        user.getRoles().remove(optionalRoles.get());
        userRepository.save(user);
        return BaseResponse.success(user.getId());
    }

    public Set<Roles> viewRoles(User user) {
        List<Roles> userRoles = user.getRoles();
        List<Roles> allRoles = roleRepository.findAll();
        Set<Roles> roles = new HashSet<>();
        for (Roles userRole : userRoles) {
            Role userRol = userRole.getRoleName();
            for (Roles role: allRoles) {
                if (userRol.getId() <= role.getRoleName().getId()) {
                    roles.add(role);
                }
            }
        }
        return roles;
    }

    public boolean checkUserRole(List<Roles> roles, Roles crmRoles) {
        for (Roles role: roles) {
            if (role.getRoleName().getId() <= crmRoles.getRoleName().getId())
                return true;
        }
        return false;
    }

    private List<AppServicesDto> getUserServices(User currentUser) {
        List<AppServicesDto> list = new ArrayList<>();
        int i = 1;
        for (ServiceList serviceList: ServiceList.values()) {
            if (serviceList.equals(ServiceList.ADD_EMPLOYEE) && currentUser.getRoles().contains(Role.BOSS))
                list.add(new AppServicesDto(i++, serviceList.name(), true));
            else
                list.add(new AppServicesDto(i++, serviceList.name(), true));
        }
        return list;
    }
}
