package com.freelance.auth_service.infrastructure.adapter.internal;

import com.freelance.auth_service.domain.entity.AuthUserEntity;
import com.freelance.auth_service.domain.repository.AuthUserRepository;
import com.freelance.auth_service.infrastructure.port.AuthUserPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthUserPersistenceAdapter implements AuthUserPersistencePort {

    private final AuthUserRepository authUserRepository;

    @Override
    public Optional<AuthUserEntity> findByEmail(String email) {
        return authUserRepository.findByEmail(email); // Query to the database
    }

    @Override
    public void save(AuthUserEntity userEntity) {
        authUserRepository.save(userEntity); // Persist user to the database
    }
}
