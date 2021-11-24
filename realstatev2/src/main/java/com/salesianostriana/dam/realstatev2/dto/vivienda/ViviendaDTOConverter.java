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

    public GetViviendaDTO viviendaToGetViviendaDTO(Vivienda v) {
        return GetViviendaDTO
                .builder()
                .id(v.getId())
                .titulo(v.getTitulo())
                .descripcion(v.getDescripcion())
                .precio(v.getPrecio())
                .ubicacion(String.format("%s, %s", v.getDireccion(), v.getCodigoPostal()))
                .zona(String.format("%s (%s)", v.getPoblacion(), v.getProvincia()))
                .metrosCuadrados(v.getMetrosCuadrados())
                .numBanios(v.getNumBanios())
                .numHabitaciones(v.getNumHabitaciones())
                .avatar(v.getAvatar())
                .tipo(v.getTipoVivienda().getTexto())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .tienePiscina(v.isTienePiscina())
                .propietario_name(v.getPropietario()==null?null:v.getPropietario().getNombre())
                .propietario_apellidos(v.getPropietario()==null?null:v.getPropietario().getApellidos())
                .propietario_email(v.getPropietario()==null?null:v.getPropietario().getEmail())

                .build();
    }
}
