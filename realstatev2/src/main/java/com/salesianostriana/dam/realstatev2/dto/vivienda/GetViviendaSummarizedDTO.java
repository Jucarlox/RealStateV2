package com.salesianostriana.dam.realstatev2.dto.vivienda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetViviendaSummarizedDTO {

    private Long id;
    private String titulo, descripcion, avatar;
    private double precio;

}
