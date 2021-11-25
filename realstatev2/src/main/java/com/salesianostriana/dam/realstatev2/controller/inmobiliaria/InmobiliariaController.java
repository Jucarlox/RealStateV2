package com.salesianostriana.dam.realstatev2.controller.inmobiliaria;

import com.salesianostriana.dam.realstatev2.dto.inmobiliaria.ConverterInmobiliariaDto;
import com.salesianostriana.dam.realstatev2.dto.inmobiliaria.CreateInmobiliariaGestorDto;
import com.salesianostriana.dam.realstatev2.dto.inmobiliaria.GetInmobiliariaDto;
import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.services.InmobiliariaService;
import com.salesianostriana.dam.realstatev2.services.ViviendaService;
import com.salesianostriana.dam.realstatev2.users.dto.CreateUserDto;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import com.salesianostriana.dam.realstatev2.users.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {

    private final ViviendaService viviendaService;
    private final InmobiliariaService inmobiliariaService;
    private final UserEntityService userEntityService;
    private final ConverterInmobiliariaDto converterInmobiliariaDto;
    @Operation(summary = "Crea una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha creado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado la inmobiliaria",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Inmobiliaria> create (@RequestBody Inmobiliaria inmobiliaria){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inmobiliariaService.save(inmobiliaria));

    }





    @PostMapping("/{id}/gestor")
    public ResponseEntity<Inmobiliaria> createInmobiliariaWithGestor (@PathVariable Long id, @RequestBody CreateUserDto GestorDto, @AuthenticationPrincipal User userLogged) {

        Inmobiliaria inmobiliaria = inmobiliariaService.getById(id);

        Boolean comprobacion = false;
        for (User gestor : inmobiliaria.getGestores()) {
            if (gestor.getId().equals(userLogged.getId())) {
                comprobacion = true;
            }
        }


        if (!userLogged.getRoles().equals(UserRole.ADMIN) && !comprobacion) {
            return ResponseEntity.notFound().build();
        } else {
            userEntityService.saveGestorWithoutId(GestorDto,inmobiliaria);
            //converterInmobiliariaDto.inmobiliariaToGetInmobiliariaDTO(inmobiliaria)

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(inmobiliariaService.save(inmobiliaria));

        }

    }

    @DeleteMapping("/gestor/{id}")
    public ResponseEntity delete(@PathVariable UUID id, @AuthenticationPrincipal User userLogged){

        Optional<User> gestor = userEntityService.loadUserById(id);


        if (!userLogged.getRoles().equals(UserRole.ADMIN) && gestor.get().getId().equals(userLogged.getId())) {
            return ResponseEntity.status(403).build();
        }else {
            Inmobiliaria inmobiliaria = gestor.get().getInmobiliaria();
            gestor.get().removeInmobiliaria(inmobiliaria);
            userEntityService.save(gestor.get());
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}/gestor")
    public ResponseEntity<Inmobiliaria> getGestoresOfInmobiliaria(@PathVariable Long id, @AuthenticationPrincipal User userLogged){
        Inmobiliaria inmobiliaria = inmobiliariaService.getById(id);

        Boolean comprobacion = false;
        for (User gestor : inmobiliaria.getGestores()) {
            if (gestor.getId().equals(userLogged.getId())) {
                comprobacion = true;
            }
        }

        if(!userLogged.getRoles().equals(UserRole.ADMIN) && !comprobacion) {
            return ResponseEntity.status(403).build();
        }else {
            return ResponseEntity.ok().body(inmobiliaria);
        }

    }


    @Operation(summary = "Obtiene todos las inmobiliarias creadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las inmobiliarias",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado las inmobiliarias",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetInmobiliariaDto>> findAll(){
        List <Inmobiliaria> datos= inmobiliariaService.findAll();

        if (datos.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            List<GetInmobiliariaDto> lista = datos.stream()
                    .map(converterInmobiliariaDto::getInmobiliariaToInmobiliariaDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(lista);
        }

    }




}
