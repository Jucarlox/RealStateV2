package com.salesianostriana.dam.realstatev2.dto.interesa;

import com.salesianostriana.dam.realstatev2.model.Interesa;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.time.LocalDateTime;

@Component
public class InteresadoDTOConverter {

    public User a(GetInteresadoDTO i) {
        return User.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .apellidos(i.getApellidos())
                .direccion(i.getDireccion())
                .avatar(i.getAvatar())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .roles(UserRole.PROPIETARIO)
                .build();
    }

    public Interesa b(GetInteresaDTO i) {
        return Interesa.builder()
                .createdDate(LocalDateTime.now())
                .mensaje(i.getMensaje())
                .build();
    }
}
