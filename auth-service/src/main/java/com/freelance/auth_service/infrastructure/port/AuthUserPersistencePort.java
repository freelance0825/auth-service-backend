package com.freelance.auth_service.infrastructure.port;

import com.freelance.auth_service.domain.entity.AuthUserEntity;

import java.util.Optional;

public interface AuthUserPersistencePort {

    Optional<AuthUserEntity> findByEmail(String email);

    void save(AuthUserEntity authUserEntity);
}
