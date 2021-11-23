package com.salesianostriana.dam.realstatev2.controller.propietario;

import com.salesianostriana.dam.realstatev2.dto.propietario.GetPropietarioViviendaDto;
import com.salesianostriana.dam.realstatev2.dto.propietario.PropietarioDTOConverter;
import com.salesianostriana.dam.realstatev2.security.jwt.JwtAuthorizationFilter;
import com.salesianostriana.dam.realstatev2.security.jwt.JwtProvider;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import com.salesianostriana.dam.realstatev2.users.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
public class PropietarioController {

    private final UserEntityService userEntityService;
    private final PropietarioDTOConverter propietarioDTOConverter;
    private final JwtProvider jwt;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @GetMapping("/")
    public ResponseEntity<List<User>> findAll(){

        List<User> usuarios = userEntityService.loadUserByRol(UserRole.PROPIETARIO);

        if(usuarios.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            List<User> lista = usuarios.stream()
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(lista);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<GetPropietarioViviendaDto>> findOne(@PathVariable UUID id, HttpServletRequest request){
        Optional<User> propietario = userEntityService.loadUserById(id);


        String token = jwtAuthorizationFilter.getJwtFromRequest(request);
        UUID idPropietario= jwt.getUserIdFromJwt(token);

        Optional<User> user = userEntityService.loadUserById(idPropietario);

        if( !user.get().getRoles().equals(UserRole.ADMIN) && !propietario.get().getId().equals(idPropietario)){
            return ResponseEntity.notFound().build();
        }
        else{
        List<GetPropietarioViviendaDto> propietarioDTOS= propietario.stream()
                .map(propietarioDTOConverter::propietarioToGetPropietarioViviendaDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(propietarioDTOS);
        }
    }

    @Operation(summary = "Borra un Propietario creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el propietario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {

        if(userEntityService.loadUserById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        else {
            userEntityService.deleteById(id);

            return ResponseEntity.noContent().build();
        }


    }



}
