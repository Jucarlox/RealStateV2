package com.salesianostriana.dam.realstatev2.controller.vivienda;

import com.salesianostriana.dam.realstatev2.model.Vivienda;
import com.salesianostriana.dam.realstatev2.security.jwt.JwtAuthorizationFilter;
import com.salesianostriana.dam.realstatev2.security.jwt.JwtProvider;
import com.salesianostriana.dam.realstatev2.services.ViviendaService;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.services.UserEntityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Vivienda", description = "Controller de las viviendas")
@RequestMapping("/vivienda")
public class ViviendaController {

    private final JwtProvider jwt;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UserEntityService userEntityService;
    private final ViviendaService viviendaService;

    @PostMapping("/")
    public ResponseEntity<Vivienda> createVivienda (@RequestBody Vivienda vivienda, HttpServletRequest request){

        String token = jwtAuthorizationFilter.getJwtFromRequest(request);
        UUID idPropietario= jwt.getUserIdFromJwt(token);
        Optional<User> propietario = userEntityService.loadUserById(idPropietario);

        if(vivienda.getTitulo().isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        else{
            if(propietario.get().getId()!=null)
                propietario = userEntityService.loadUserById(propietario.get().getId());

            vivienda.addPropietario(propietario.get());
            viviendaService.save(vivienda);

            return  ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(vivienda);
        }
    }


}
