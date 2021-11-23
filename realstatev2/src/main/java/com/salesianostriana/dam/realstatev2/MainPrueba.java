package com.salesianostriana.dam.realstatev2;

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



        //userEntityService.saveAdmin(user);
    }
}
