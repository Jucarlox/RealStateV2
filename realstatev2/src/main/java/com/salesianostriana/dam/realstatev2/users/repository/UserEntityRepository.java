package com.salesianostriana.dam.realstatev2.users.repository;

import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByEmail(String email);
    List<User> findByRoles (UserRole roles);

}
