package com.salesianostriana.dam.realstatev2.users.repository;

import com.salesianostriana.dam.realstatev2.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmail(String email);

}
