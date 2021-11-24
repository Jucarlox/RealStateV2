package com.salesianostriana.dam.realstatev2.dto.inmobiliaria;

import com.salesianostriana.dam.realstatev2.model.Vivienda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GetInmobiliariaDto {
    private String nombre;
    private String email;
    private String telefono;
    private List<Vivienda> viviendas;
    private String gestor_nombre;
    private String gestor_email;
}
