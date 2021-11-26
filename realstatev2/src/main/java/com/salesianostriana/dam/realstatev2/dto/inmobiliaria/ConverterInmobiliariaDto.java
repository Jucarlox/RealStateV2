package com.salesianostriana.dam.realstatev2.dto.inmobiliaria;

import com.salesianostriana.dam.realstatev2.dto.vivienda.GetViviendaDTO;
import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.model.Vivienda;
import com.salesianostriana.dam.realstatev2.services.InmobiliariaService;
import com.salesianostriana.dam.realstatev2.users.dto.GetUserDto;
import com.salesianostriana.dam.realstatev2.users.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ConverterInmobiliariaDto {




    public User convertInmobiliariaDto(CreateInmobiliariaGestorDto createInmobiliariaGestorDto) {
        return User.builder()
                .nombre(createInmobiliariaGestorDto.getNombre())
                .apellidos(createInmobiliariaGestorDto.getApellidos())
                .email(createInmobiliariaGestorDto.getEmail())
                .telefono(createInmobiliariaGestorDto.getTelefono())
                .inmobiliaria(null)
                .avatar(createInmobiliariaGestorDto.getAvatar())
                .direccion(createInmobiliariaGestorDto.getDireccion())
                .build();

    }


    /*public GetInmobiliariaDto inmobiliariaToGetInmobiliariaDTO(Inmobiliaria i){
        return GetInmobiliariaDto.builder()
                .nombre(i.getNombre())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .viviendas(i.getViviendas())
                .gestor_nombre(i.getGestores().iterator().next().getNombre())
                .gestor_email(i.getGestores().iterator().next().getEmail())

                .build();
    }*/

    public GetInmobiliariaDto getInmobiliariaToInmobiliariaDto(Inmobiliaria in){

        List<String> nombreVivienda = new ArrayList<>();
        for (int i=0; i<in.getViviendas().size();i++){
            nombreVivienda.add(in.getViviendas().get(i).getTitulo());
        }

        return GetInmobiliariaDto
                .builder()
                .id(in.getId())
                .nombre(in.getNombre())
                .email(in.getEmail())
                .telefono(in.getTelefono())
                .viviendas(nombreVivienda)
                .build();

    }

    public GetInmobiliariaGestorDto getInmobiliariaGestorDto(Inmobiliaria in){
        List<String> nombreGestor = new ArrayList<>();
        List<String> nombreVivienda = new ArrayList<>();
        for (int i=0; i<in.getViviendas().size();i++){
            nombreVivienda.add(in.getViviendas().get(i).getTitulo());
        }
        for (int i=0; i<in.getGestores().size();i++){
            nombreGestor.add(in.getGestores().get(i).getNombre());
        }
        return GetInmobiliariaGestorDto.builder()
                .id(in.getId())
                .nombre(in.getNombre())
                .telefono(in.getTelefono())
                .titulo_viviendas(nombreVivienda)
                .nombre_gestores(nombreGestor)
                .build();
    }
}
