package com.salesianostriana.dam.realstatev2.dto.inmobiliaria;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateInmobiliariaGestorDto {

    private String email;
    private String password;
    private String password2;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String avatar;

}
