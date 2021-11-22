package com.salesianostriana.dam.realstatev2.users.services;

import com.salesianostriana.dam.realstatev2.services.base.BaseService;
import com.salesianostriana.dam.realstatev2.users.dto.CreateUserDto;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.salesianostriana.dam.realstatev2.users.repository.UserEntityRepository;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<User, Long, UserEntityRepository> implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email).orElseThrow(()-> new UsernameNotFoundException("El "+ email + " no encontrado"));
    }

    public User saveAdmin(CreateUserDto newAdmin) {
        if (newAdmin.getPassword().contentEquals(newAdmin.getPassword2())) {
            User user = User.builder()
                    .password(passwordEncoder.encode(newAdmin.getPassword()))
                    .avatar(newAdmin.getAvatar())
                    .apellidos(newAdmin.getApellidos())
                    .direccion(newAdmin.getDireccion())
                    .telefono(newAdmin.getTelefono())
                    .nombre(newAdmin.getNombre())
                    .email(newAdmin.getEmail())
                    .role(UserRole.ADMIN)
                    .build();
            return save(user);
        } else {
            return null;
        }
    }

    public User saveGestor(CreateUserDto newGestor) {
        if (newGestor.getPassword().contentEquals(newGestor.getPassword2())) {
            User user = User.builder()
                    .password(passwordEncoder.encode(newGestor.getPassword()))
                    .avatar(newGestor.getAvatar())
                    .apellidos(newGestor.getApellidos())
                    .direccion(newGestor.getDireccion())
                    .telefono(newGestor.getTelefono())
                    .nombre(newGestor.getNombre())
                    .email(newGestor.getEmail())
                    .role(UserRole.GESTOR)
                    .build();
            return save(user);
        } else {
            return null;
        }
    }

    public User savePropietario(CreateUserDto newAdmin) {
        if (newAdmin.getPassword().contentEquals(newAdmin.getPassword2())) {
            User user = User.builder()
                    .password(passwordEncoder.encode(newAdmin.getPassword()))
                    .avatar(newAdmin.getAvatar())
                    .apellidos(newAdmin.getApellidos())
                    .direccion(newAdmin.getDireccion())
                    .telefono(newAdmin.getTelefono())
                    .nombre(newAdmin.getNombre())
                    .email(newAdmin.getEmail())
                    .role(UserRole.ADMIN)
                    .build();
            return save(user);
        } else {
            return null;
        }
    }
}
