package com.epam.core.repository;

import com.epam.core.entity.UserAccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountRepository extends CrudRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByUserId(long userID);
}
