package com.freelance.auth_service.shared.util;

import com.freelance.auth_service.domain.entity.AuthUserEntity;
import com.freelance.auth_service.infrastructure.port.AuthUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthUserPersistencePort authUserPersistencePort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUserEntity authUserEntity = authUserPersistencePort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(authUserEntity.getEmail())
                .password(authUserEntity.getPassword())
                .authorities("USER") // Add roles if needed
                .build();
    }
}
