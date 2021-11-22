package com.salesianostriana.dam.realstatev2.users.services;

import com.salesianostriana.dam.realstatev2.services.base.BaseService;
import com.salesianostriana.dam.realstatev2.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.salesianostriana.dam.realstatev2.users.repository.UserEntityRepository;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, Long, UserEntityRepository> implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
