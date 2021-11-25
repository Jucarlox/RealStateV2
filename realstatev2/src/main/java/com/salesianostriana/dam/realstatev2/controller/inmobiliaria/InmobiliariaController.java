package com.salesianostriana.dam.realstatev2.controller.inmobiliaria;

import com.salesianostriana.dam.realstatev2.dto.inmobiliaria.ConverterInmobiliariaDto;
import com.salesianostriana.dam.realstatev2.dto.inmobiliaria.CreateInmobiliariaGestorDto;
import com.salesianostriana.dam.realstatev2.dto.inmobiliaria.GetInmobiliariaDto;
import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.model.Vivienda;
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
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado la inmobiliaria",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Inmobiliaria> create(@RequestBody Inmobiliaria inmobiliaria) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inmobiliariaService.save(inmobiliaria));

    }


    @Operation(summary = "Añade un nuevo usuario gestor a la inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha añade gestor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha añadido el gestor",
                    content = @Content),
    })
    @PostMapping("/{id}/gestor")
    public ResponseEntity<Inmobiliaria> createInmobiliariaWithGestor(@PathVariable Long id, @RequestBody CreateUserDto GestorDto, @AuthenticationPrincipal User userLogged) {

        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);
        if (inmobiliaria.isPresent()) {
            if (!userLogged.getRoles().equals(UserRole.ADMIN) && !inmobiliariaService.comprobacion(inmobiliaria.get(), userLogged)) {
                return ResponseEntity.notFound().build();
            } else {
                userEntityService.saveGestorWithoutId(GestorDto, inmobiliaria.get());
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(inmobiliariaService.save(inmobiliaria.get()));
            }
        } else return ResponseEntity.notFound().build();

    }

    @Operation(summary = "Elimina a un usuario gestor de una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminado el gestor",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
    @DeleteMapping("/gestor/{id}")
    public ResponseEntity delete(@PathVariable UUID id, @AuthenticationPrincipal User userLogged) {

        Optional<User> gestor = userEntityService.loadUserById(id);

        if(gestor.isPresent()){
        if (!userLogged.getRoles().equals(UserRole.ADMIN) && gestor.get().getId().equals(userLogged.getId())) {
            return ResponseEntity.status(403).build();
        } else {
            Inmobiliaria inmobiliaria = gestor.get().getInmobiliaria();
            gestor.get().removeInmobiliaria(inmobiliaria);
            userEntityService.save(gestor.get());
            return ResponseEntity.noContent().build();
        }
        }else return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene todos los gestores de una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los gestores",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado los gestores",
                    content = @Content),
    })
    @GetMapping("/{id}/gestor")
    public ResponseEntity<Inmobiliaria> getGestoresOfInmobiliaria(@PathVariable Long id, @AuthenticationPrincipal User userLogged) {
        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);


        if(inmobiliaria.isPresent()) {
            if (!userLogged.getRoles().equals(UserRole.ADMIN) && !inmobiliariaService.comprobacion(inmobiliaria.get(), userLogged)) {
                return ResponseEntity.status(403).build();
            } else {
                return ResponseEntity.ok().body(inmobiliaria.get());
            }
        }else return ResponseEntity.noContent().build();

    }


    @Operation(summary = "Obtiene todos las inmobiliarias creadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las inmobiliarias",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado las inmobiliarias",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetInmobiliariaDto>> findAll() {
        List<Inmobiliaria> datos = inmobiliariaService.findAll();

        if (datos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GetInmobiliariaDto> lista = datos.stream()
                    .map(converterInmobiliariaDto::getInmobiliariaToInmobiliariaDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(lista);
        }

    }


    @Operation(summary = "Obtiene una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la inmobiliaria",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado la inmobiliaria",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<GetInmobiliariaDto>> findOne(@PathVariable Long id) {
        Optional<Inmobiliaria> inmo = inmobiliariaService.findById(id);
        if (inmobiliariaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GetInmobiliariaDto> inmobiliariaDTO = inmo.stream()
                    .map(converterInmobiliariaDto::getInmobiliariaToInmobiliariaDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(inmobiliariaDTO);
        }
    }


    @Operation(summary = "Borra una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la inmobiliaria",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<GetInmobiliariaDto> borrarInmobiliaria(@PathVariable Long id) {

        if (inmobiliariaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);

            for (Vivienda vivienda : inmobiliaria.get().getViviendas()) {
                vivienda.setInmobiliaria(null);
            }

            for (User gestor : inmobiliaria.get().getGestores()) {
                gestor.setInmobiliaria(null);
            }


            inmobiliariaService.deleteById(id);

            return ResponseEntity.noContent().build();
        }
    }


}
