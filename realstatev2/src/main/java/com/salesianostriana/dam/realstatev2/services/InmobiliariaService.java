package com.salesianostriana.dam.realstatev2.services;

import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.repository.InmobiliariaRepository;
import com.salesianostriana.dam.realstatev2.services.base.BaseService;
import com.salesianostriana.dam.realstatev2.users.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class InmobiliariaService extends BaseService<Inmobiliaria, Long, InmobiliariaRepository> {

    public Boolean findGestorOfViviendaId ( Inmobiliaria inmobiliaria){

        return repositorio.findByGestoresId(inmobiliaria);

    }


    public Boolean comprobacion (Inmobiliaria inmobiliaria, @AuthenticationPrincipal User userLogged){
        Boolean comprobacion = false;
            for (User gestor : inmobiliaria.getGestores()) {
                if (gestor.getId().equals(userLogged.getId())) {
                    comprobacion=true;

                }
            }
        return comprobacion;
    }
}
