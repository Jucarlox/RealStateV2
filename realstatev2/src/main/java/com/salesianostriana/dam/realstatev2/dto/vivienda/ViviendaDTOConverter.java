package com.salesianostriana.dam.realstatev2.dto.vivienda;

import com.salesianostriana.dam.realstatev2.model.Vivienda;
import org.springframework.stereotype.Component;

@Component
public class ViviendaDTOConverter {

    public GetViviendaSummarizedDTO viviendaToGetViviendaSummarizedDTO(Vivienda m) {
        return GetViviendaSummarizedDTO
                .builder()
                .id(m.getId())
                .titulo(m.getTitulo())
                .descripcion(m.getDescripcion())
                .avatar(m.getAvatar())
                .precio(m.getPrecio())
                .build();

    }
}
