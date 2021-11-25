package com.salesianostriana.dam.realstatev2.users.controller;

import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.model.Interesa;
import com.salesianostriana.dam.realstatev2.services.InmobiliariaService;
import com.salesianostriana.dam.realstatev2.services.InteresaService;
import com.salesianostriana.dam.realstatev2.users.dto.CreateGestorDto;
import com.salesianostriana.dam.realstatev2.users.dto.CreateUserDto;
import com.salesianostriana.dam.realstatev2.users.dto.GetUserDto;
import com.salesianostriana.dam.realstatev2.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final InmobiliariaService inmobiliariaService;
    private final InteresaService interesaService;

    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto newPropietario) {
        User saved = userEntityService.savePropietario(newPropietario);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }

    @PostMapping("/auth/register/gestor")
    public ResponseEntity<GetUserDto> nuevoGestor(@RequestBody CreateGestorDto newGestor) {




        User saved= userEntityService.saveGestor(newGestor);

        if (saved == null || saved.getInmobiliaria()== null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }

    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> nuevoAdmin(@RequestBody CreateUserDto newAdmin) {
        User saved = userEntityService.saveAdmin(newAdmin);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }


    @GetMapping("/interesado/")
    public ResponseEntity<List<User>> getInteresados (){

        List<Interesa> interesas= interesaService.findInteresados();
        List<User> interesados= new ArrayList<>();

        for (Interesa i: interesas){
            interesados.add(i.getInteresado());
        }
    return  ResponseEntity.ok(interesados);
    }

}
