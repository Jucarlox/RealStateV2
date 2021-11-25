package com.salesianostriana.dam.realstatev2.dto.inmobiliaria;

import com.salesianostriana.dam.realstatev2.model.Vivienda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class GetInmobiliariaDto {

    private Long id;

    private String nombre,telefono,email;
    private List<String> viviendas;



}
