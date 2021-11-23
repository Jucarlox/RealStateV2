package com.salesianostriana.dam.realstatev2;

import com.salesianostriana.dam.realstatev2.model.Tipo;
import com.salesianostriana.dam.realstatev2.model.Vivienda;
import com.salesianostriana.dam.realstatev2.services.ViviendaService;
import com.salesianostriana.dam.realstatev2.users.dto.CreateUserDto;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MainPrueba {

    private final UserEntityService userEntityService;
    private final ViviendaService viviendaService;
    @PostConstruct
    public void test() {

        CreateUserDto user= CreateUserDto.builder()
                .nombre("Jaime")
                .apellidos("Jimenez")
                .telefono("438070987")
                .direccion("C/ Mi calle")
                .avatar("png")
                .email("jaime@gmail.com")
                .password("1234")
                .password2("1234")
                .build();

        userEntityService.saveAdmin(user);

        CreateUserDto user2= CreateUserDto.builder()
                .nombre("Juanka")
                .apellidos("Jimenez")
                .telefono("438070987")
                .direccion("C/ Mi calle")
                .avatar("png")
                .email("juanka@gmail.com")
                .password("1234")
                .password2("1234")
                .build();



        Vivienda vivienda1 = Vivienda.builder()
                .titulo("Casa 2")
                .descripcion("Descripcion")
                .precio(2354567.78)
                .tipoVivienda(Tipo.VENTA)
                .build();

        vivienda1.addPropietario(userEntityService.savePropietario(user2));

        viviendaService.save(vivienda1);









        //userEntityService.saveAdmin(user);
    }
}
