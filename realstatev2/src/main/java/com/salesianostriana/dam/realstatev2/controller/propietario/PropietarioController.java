package com.salesianostriana.dam.realstatev2.controller.propietario;

import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import com.salesianostriana.dam.realstatev2.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
public class PropietarioController {
    private final UserEntityService userEntityService;

    @GetMapping("/")
    public ResponseEntity<List<User>> findAll(){

        List<User> datos = userEntityService.loadUserByRol(UserRole.PROPIETARIO);

        if(datos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            List<User> lista = datos.stream()
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(lista);
        }
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<List<GetPropietarioViviendaDto>> findOne(@PathVariable Long id){
        Optional<Propietario> prop = propietarioService.findById(id);
        if(propietarioService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            List<GetPropietarioViviendaDto> propietarioDTOS= prop.stream()
                    .map(propietarioDTOConverter::propietarioToGetPropietarioViviendaDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(propietarioDTOS);
        }
    }*/
}
