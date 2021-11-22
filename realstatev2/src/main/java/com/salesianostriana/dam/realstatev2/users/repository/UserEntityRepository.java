package com.salesianostriana.dam.realstatev2.users.repository;

import com.salesianostriana.dam.realstatev2.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

}
