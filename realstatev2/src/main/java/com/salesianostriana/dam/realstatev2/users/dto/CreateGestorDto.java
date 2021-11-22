package com.salesianostriana.dam.realstatev2.users.dto;

import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGestorDto {
    private String email;
    private String password;
    private String password2;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String avatar;
    private Inmobiliaria inmobiliaria;
}
