package com.zulfiqor.z_crm_zulfiqor.utils;

import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            String phoneNumber = (String) authentication.getPrincipal();
            User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
            return user;
        }
        return null;
    }
}
