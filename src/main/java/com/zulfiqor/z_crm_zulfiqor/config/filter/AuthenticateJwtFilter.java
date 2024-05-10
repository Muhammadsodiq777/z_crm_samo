package com.zulfiqor.z_crm_zulfiqor.config.filter;

import com.google.gson.Gson;
import com.zulfiqor.z_crm_zulfiqor.exception.HttpResponseCode;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.Roles;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.entity.UserToken;
import com.zulfiqor.z_crm_zulfiqor.model.enums.UserStatus;
import com.zulfiqor.z_crm_zulfiqor.repository.UserRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.UserTokenRepository;
import com.zulfiqor.z_crm_zulfiqor.utils.JwtProviderUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class AuthenticateJwtFilter extends OncePerRequestFilter {
    private final UserTokenRepository userTokenRepository;

    public AuthenticateJwtFilter(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            UserToken userToken = userTokenRepository.findByAccessToken(headerAuth.substring(7));
            if (userToken != null && userToken.getAccessExpireDate().after(new Date())) {
                User userDetails = userToken.getUser();
                if (userDetails != null) {
                    if (userDetails.getStatus().equals(UserStatus.ACTIVE)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getPhoneNumber(), userDetails.getPassword(), getAuthorities(userDetails.getRoles()));
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        logger.info("User authenticated:");
                    } else if (!request.getRequestURI().contains("/api/v1/auth")) {
                        response.setStatus(HttpResponseCode.BLOCKED.getCode());
                        PrintWriter writer = response.getWriter();
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        writer.print(getBlockResponse());
                        writer.flush();
                        return;
                    }
                }
            } else if (!request.getRequestURI().contains("/api/v1/auth")) {
                response.setStatus(HttpResponseCode.TOKEN_EXPIRE.getCode());
                PrintWriter writer = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                writer.print(getExpireToken());
                writer.flush();
                return;
            }
        }

        filterChain.doFilter(request, response);
//        logger.info("Request Keldi: " + request.getRequestURI());
//        logger.info("Status code: " + response.getStatus());
    }

    private String getExpireToken() {
        BaseResponse<?> response = BaseResponse.error(HttpResponseCode.TOKEN_EXPIRE.getCode(), HttpResponseCode.TOKEN_EXPIRE.getMessage());
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    private String getBlockResponse() {
        BaseResponse<?> response = BaseResponse.error(HttpResponseCode.BLOCKED.getCode(), HttpResponseCode.BLOCKED.getMessage());
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<Roles> roles) {
        return new ArrayList<GrantedAuthority>(roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name())).toList());
    }
}
