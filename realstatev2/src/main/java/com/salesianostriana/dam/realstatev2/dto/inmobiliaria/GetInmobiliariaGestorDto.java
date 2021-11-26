package com.salesianostriana.dam.realstatev2.dto.inmobiliaria;

import com.salesianostriana.dam.realstatev2.model.Vivienda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetInmobiliariaGestorDto {
    private Long id;
    private String nombre;
    private String telefono;
    private List<String> titulo_viviendas;
    private List<String> nombre_gestores;
}
