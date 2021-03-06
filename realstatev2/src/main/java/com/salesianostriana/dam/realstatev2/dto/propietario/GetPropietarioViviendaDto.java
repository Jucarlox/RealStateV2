package com.salesianostriana.dam.realstatev2.dto.propietario;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPropietarioViviendaDto {
    private UUID id;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private List<String> direccionVivienda;
}
