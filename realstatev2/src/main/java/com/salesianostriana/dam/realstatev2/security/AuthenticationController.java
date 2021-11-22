package com.salesianostriana.dam.realstatev2.security;

import com.salesianostriana.dam.realstatev2.security.dto.LoginDto;
import com.salesianostriana.dam.realstatev2.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    //private final AuthenticationManager authenticationManager;


    /*@PostMapping("/auth/register/user")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );


    }*/
}
