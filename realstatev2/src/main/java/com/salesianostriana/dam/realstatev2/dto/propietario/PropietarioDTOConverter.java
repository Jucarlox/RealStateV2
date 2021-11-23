package com.salesianostriana.dam.realstatev2.dto.propietario;

import com.salesianostriana.dam.realstatev2.users.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PropietarioDTOConverter {
    public GetPropietarioViviendaDto propietarioToGetPropietarioViviendaDto(User u){

        List<String> direccion= new ArrayList<>();

        for (int i = 0;i<u.getViviendas().size();i++){
            direccion.add(u.getViviendas().get(i).getDireccion());
        }
        return GetPropietarioViviendaDto
                .builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .apellidos(u.getApellidos())
                .direccion(u.getDireccion())
                .email(u.getEmail())
                .telefono(u.getTelefono())
                .direccionVivienda(direccion)
                .build();
    }
}
