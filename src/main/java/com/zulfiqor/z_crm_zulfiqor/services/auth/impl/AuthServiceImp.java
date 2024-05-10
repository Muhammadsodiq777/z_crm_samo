package com.zulfiqor.z_crm_zulfiqor.services.auth.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.BadRequestException;
import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.LoginRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.RefreshTokenRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.RegisterRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.AuthenticationResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.Device;
import com.zulfiqor.z_crm_zulfiqor.model.entity.Roles;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.entity.UserToken;
import com.zulfiqor.z_crm_zulfiqor.model.enums.Role;
import com.zulfiqor.z_crm_zulfiqor.model.enums.UserStatus;
import com.zulfiqor.z_crm_zulfiqor.repository.DeviceRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.RoleRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.UserRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.UserTokenRepository;
import com.zulfiqor.z_crm_zulfiqor.services.auth.AuthService;
import com.zulfiqor.z_crm_zulfiqor.utils.JwtProviderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImp implements AuthService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserTokenRepository userTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeviceRepository deviceRepository;

    public AuthServiceImp(UserRepository userRepository, RoleRepository roleRepository, UserTokenRepository userTokenRepository, PasswordEncoder passwordEncoder, DeviceRepository deviceRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userTokenRepository = userTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public SaveResponse registerUser(RegisterRequest request) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (optionalUser.isPresent()) {
            throw new BadRequestException("Ushbu nomerda foydalanuvchi mavjud iltimos.");
        }

//        List<User> bosses = userRepository.findAllByRoleName(List.of(Role.BOSS.name()));
//        if (!bosses.isEmpty())
//            throw new BadRequestException("Bu service orqali registratsiya qila olmaysiz");
        User newUser = new User();
        newUser.setFio(request.getFio());
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setActive(true);
        Roles role = getRoleByName(Role.BOSS);
        newUser.setRoles(List.of(role));
        userRepository.save(newUser);
        return new SaveResponse(newUser.getId());
    }

    @Override
    public BaseResponse<?> loginUser(LoginRequest request, String deviceId) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(request.getPhone());
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Bunday foydalanuvchi topilmadi. Iltimos qayta urinib ko'ring");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotFoundException("Parol notogri kiritdingiz!");
        }
        Date accessExpirationDate = expirationDate(JwtProviderUtil.ACCESS_EXPIRATION_TIME);
        Date refreshExpirationDate = expirationDate(JwtProviderUtil.REFRESH_EXPIRATION_TIME);
        String token = JwtProviderUtil.generateToken(user, accessExpirationDate);
        String refreshToken = JwtProviderUtil.generateRefreshToken(refreshExpirationDate);
        saveToken(user, token, refreshToken, accessExpirationDate, refreshExpirationDate, deviceId);
        userRepository.save(user);
        return BaseResponse.success(new AuthenticationResponse(user.getId(), user.getFio(), token, refreshToken));
    }

    @Override
    public BaseResponse<?> refreshToken(RefreshTokenRequest refreshTokenRequest, String deviceId) {
        UserToken userToken = userTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken());
        if (userToken == null || userToken.getRefreshExpireDate().before(new Date())) {
            throw new UsernameNotFoundException("Token is expired. Please get new token");
        }
        User user = userToken.getUser();
        if (user == null) {
            throw new NotFoundException("Bunday foydalanuvchi topilmadi. Iltimos qayta urinib ko'ring");
        }
        Date accessExpirationDate = expirationDate(JwtProviderUtil.ACCESS_EXPIRATION_TIME);
        Date refreshExpirationDate = expirationDate(JwtProviderUtil.REFRESH_EXPIRATION_TIME);
        String token = JwtProviderUtil.generateToken(user, accessExpirationDate);
        String refreshToken = JwtProviderUtil.generateRefreshToken(refreshExpirationDate);
        saveToken(user, token, refreshToken, accessExpirationDate, refreshExpirationDate, deviceId);
        userRepository.save(user);
        return BaseResponse.success(new AuthenticationResponse(user.getId(), user.getFio(), token, refreshToken));
    }


    public Roles getRoleByName(Role role) {
        Roles roleEntity = roleRepository.findByRoleName(role);
        if (roleEntity == null) {
            roleEntity = new Roles();
            roleEntity.setRoleName(role);
            roleRepository.save(roleEntity);
        }
        return roleEntity;
    }

    private List<String> getPermissionForToken(List<Roles> roles) {
        List<String> permissions = new ArrayList<>();
        roles.forEach(role -> permissions.add(role.getRoleName().name()));
        return permissions;
    }

    private Date expirationDate(Long expirationTime) {
        long time = new Date().getTime() + expirationTime;
        return new Date(time);
    }

    private void saveToken(User user, String token, String refreshToken, Date accessExpirationDate, Date refreshExpirationDate, String deviceId) {
        UserToken userToken = new UserToken(token, refreshToken, accessExpirationDate, refreshExpirationDate, !deviceId.equals("") ? deviceId : null, user);
        Device device = deviceRepository.findByDeviceId(deviceId);
        if (device == null) {
            device = new Device();
            device.setDeviceId(deviceId);
            device.setUserId(user.getId());
            device.setUserAgent(deviceId);
            deviceRepository.save(device);
        }
        userToken.setDevice(device);
        userTokenRepository.save(userToken);
    }
}
