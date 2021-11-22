package com.salesianostriana.dam.realstatev2.users.controller;

import com.salesianostriana.dam.realstatev2.users.dto.CreateUserDto;
import com.salesianostriana.dam.realstatev2.users.dto.GetUserDto;
import com.salesianostriana.dam.realstatev2.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto newPropietario) {
        User saved = userEntityService.savePropietario(newPropietario);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }

    @PostMapping("/auth/register/gestor")
    public ResponseEntity<GetUserDto> nuevoGestor(@RequestBody CreateUserDto newGestor) {
        User saved = userEntityService.saveGestor(newGestor);

        if (saved == null)
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


}
