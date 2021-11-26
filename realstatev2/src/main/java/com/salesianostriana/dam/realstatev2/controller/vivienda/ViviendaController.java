package com.salesianostriana.dam.realstatev2.controller.vivienda;

import com.salesianostriana.dam.realstatev2.dto.interesa.GetInteresaDTO;
import com.salesianostriana.dam.realstatev2.dto.interesa.GetInteresadoDTO;
import com.salesianostriana.dam.realstatev2.dto.interesa.InteresadoDTOConverter;
import com.salesianostriana.dam.realstatev2.dto.vivienda.GetViviendaDTO;
import com.salesianostriana.dam.realstatev2.dto.vivienda.GetViviendaSummarizedDTO;
import com.salesianostriana.dam.realstatev2.dto.vivienda.ViviendaDTOConverter;
import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.model.Interesa;
import com.salesianostriana.dam.realstatev2.model.Vivienda;
import com.salesianostriana.dam.realstatev2.repository.InteresaRepository;
import com.salesianostriana.dam.realstatev2.security.jwt.JwtAuthorizationFilter;
import com.salesianostriana.dam.realstatev2.security.jwt.JwtProvider;
import com.salesianostriana.dam.realstatev2.services.InmobiliariaService;
import com.salesianostriana.dam.realstatev2.services.InteresaService;
import com.salesianostriana.dam.realstatev2.services.ViviendaService;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import com.salesianostriana.dam.realstatev2.users.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Vivienda", description = "Controller de las viviendas")
@RequestMapping("/vivienda")
public class ViviendaController {

    private final JwtProvider jwt;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UserEntityService userEntityService;
    private final ViviendaService viviendaService;
    private final InmobiliariaService inmobiliariaService;
    private final ViviendaDTOConverter viviendaDTOConverter;
    private final InteresadoDTOConverter interesadoDTOConverter;
    private final InteresaService interesaService;

    @Operation(summary = "Crea una nueva vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado la vivienda",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Vivienda> createVivienda(@RequestBody Vivienda vivienda, @AuthenticationPrincipal User userLogged) {

        if (vivienda.getTitulo().isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            vivienda.addPropietario(userLogged);
            viviendaService.save(vivienda);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vivienda);
        }
    }

    @Operation(summary = "Se listan todas las viviendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las viviendas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado las viviendas",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetViviendaSummarizedDTO>> findAll() {

        List<Vivienda> datos = viviendaService.findAll();
        if (datos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GetViviendaSummarizedDTO> lista = datos.stream()
                    .map(viviendaDTOConverter::viviendaToGetViviendaSummarizedDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(lista);
        }
    }

    @Operation(summary = "Obtiene una vivienda creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la vivienda",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetViviendaDTO> findOne(@PathVariable Long id) {

        Optional<Vivienda> vivienda = viviendaService.findById(id);

        if (vivienda.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            GetViviendaDTO viviendaDTO = viviendaDTOConverter.viviendaToGetViviendaDTO(vivienda.get());
            return ResponseEntity.ok().body(viviendaDTO);
        }
    }

    @Operation(summary = "Edita una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha editado la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha editado la vivienda",
                    content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vivienda> edit(@RequestBody Vivienda v, @PathVariable Long id, @AuthenticationPrincipal User userLogged) {

    Optional<Vivienda> vivienda= viviendaService.findById(id);

        if (vivienda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else {
            if (!userLogged.getRoles().equals(UserRole.ADMIN) && !vivienda.get().getPropietario().getId().equals(userLogged.getId())) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.of(
                        viviendaService.findById(id).map(m -> {
                            m.setTitulo(v.getTitulo());
                            m.setDescripcion(v.getDescripcion());
                            m.setAvatar(v.getAvatar());
                            m.setCodigoPostal(v.getCodigoPostal());
                            m.setLatlng(v.getLatlng());
                            m.setMetrosCuadrados(v.getMetrosCuadrados());
                            m.setNumBanios(v.getNumBanios());
                            m.setNumHabitaciones(v.getNumHabitaciones());
                            m.setPoblacion(v.getPoblacion());
                            m.setPrecio(v.getPrecio());
                            m.setProvincia(v.getProvincia());
                            m.setDireccion(v.getDireccion());
                            m.setTipoVivienda(v.getTipoVivienda());
                            m.setTienePiscina(v.isTienePiscina());
                            m.setTieneAscensor(v.isTieneAscensor());
                            m.setTieneGaraje(v.isTieneGaraje());
                            viviendaService.save(m);
                            return m;
                        })
                );
            }
        }
    }

    @Operation(summary = "Borra una vivienda por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, @AuthenticationPrincipal User userLogged) {

        Optional<Vivienda> vivienda= viviendaService.findById(id);
        if (vivienda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else {
            if (!userLogged.getRoles().equals(UserRole.ADMIN) && !vivienda.get().getPropietario().getId().equals(userLogged.getId())) {
                return ResponseEntity.notFound().build();
            } else {
                viviendaService.deleteById(id);
                return ResponseEntity.noContent().build();
            }
        }
    }

    @Operation(summary = "Establece una vivienda gestionada por una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha establecido la relacion de gestion",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha establecido la relacion de gestion",
                    content = @Content),
    })

    @PostMapping("{id}/inmobiliaria/{id2}")
    public ResponseEntity<GetViviendaDTO> relationViviendaInmobiliaria(@PathVariable Long id, @PathVariable Long id2, @AuthenticationPrincipal User userLogged) {

        Optional<Vivienda> vivienda= viviendaService.findById(id);
        if (vivienda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else {
            if (!userLogged.getRoles().equals(UserRole.ADMIN) && !vivienda.get().getPropietario().getId().equals(userLogged.getId())) {
                return ResponseEntity.notFound().build();
            } else {
                vivienda.get().addInmobiliaria(inmobiliariaService.getById(id2));
                userLogged.addInmobiliaria(inmobiliariaService.getById(id2));
                userEntityService.save(userLogged);
                viviendaService.save(vivienda.get());
                GetViviendaDTO viviendaDTO = viviendaDTOConverter.viviendaToGetViviendaDTO(vivienda.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(viviendaDTO);
            }
        }
    }

    @Operation(summary = "Borra la asociación entre una vivienda y una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la vivienda a la que le queremos quitar la inmobiliaria",
                    content = @Content),
    })

    @DeleteMapping("/{id}/inmobiliaria")
    public ResponseEntity deleteInmobiliariaFromVivienda(@PathVariable Long id, @AuthenticationPrincipal User userLogged) {

        Optional<Vivienda> vivienda= viviendaService.findById(id);
        if(vivienda.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            Boolean comprobacion=viviendaService.comprobacion(vivienda.get(), userLogged);
            if (!viviendaService.findById(id).isEmpty() && !userLogged.getRoles().equals(UserRole.ADMIN) &&  !viviendaService.findById(id).get().getPropietario().getId().equals(userLogged.getId()) && !comprobacion) {
                return ResponseEntity.notFound().build();
            } else {
                Inmobiliaria inmobiliaria = viviendaService.findById(id).get().getInmobiliaria();
                viviendaService.findById(id).get().removeInmobiliaria(inmobiliaria);
                viviendaService.save(viviendaService.findById(id).get());
                return ResponseEntity.noContent().build();
            }
        }
    }

    @Operation(summary = "Crea un nuevo interesado e interesa asociado a una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el interesado y el interesa asociado a una vivienda",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado el interesado y el interesa asociado a una vivienda",
                    content = @Content),
    })
    @PostMapping("/{id}/meinteresa")
    public ResponseEntity<User> createInteresa (@RequestBody GetInteresaDTO dto, @PathVariable Long id, @AuthenticationPrincipal User userLogged){

        Optional<Vivienda> vivienda=viviendaService.findById(id);

        if(vivienda.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            Interesa interesa = interesadoDTOConverter.b(dto);

            interesa.addInteresado(userLogged);
            interesa.addVivienda(vivienda.get());

            interesaService.save(interesa);
            userLogged.getInteresas().add(interesa);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userEntityService.save(userLogged));
        }
    }

    @Operation(summary = "Elimina a un usuario gestor de una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el gestor de la inmobiliario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la vivienda a la que le queremos quitar la inmobiliaria",
                    content = @Content),
    })
    @DeleteMapping("/{id}/meinteresa")
    public ResponseEntity<?> deleteInteres(@PathVariable Long id, @AuthenticationPrincipal User userLogged) {

        Optional<Vivienda> vivienda=viviendaService.findById(id);
        if(vivienda.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            List<Interesa> interesaList = vivienda.get().getInteresas();
            for (Interesa i : interesaList){
                if(i.getInteresado().getId().equals(userLogged.getId())){
                    interesaService.delete(i);
                    return ResponseEntity.noContent().build();
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Obtiene un top de las 5 viviendas con más interesados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las viviendas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})
    })
    @GetMapping(value = "/top", params = {"n"})
    public ResponseEntity<List<GetViviendaSummarizedDTO>> top10Viviendas (@RequestParam("n") int n) {
        List<Vivienda> datos = viviendaService.findTop10ViviendaOrderByInteresas();
        if(datos.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            List<GetViviendaSummarizedDTO> lista = datos.stream()
                    .map(viviendaDTOConverter::viviendaToGetViviendaSummarizedDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(lista);
        }

    }








}






