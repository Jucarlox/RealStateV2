package com.salesianostriana.dam.realstatev2.services;

import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.model.Vivienda;
import com.salesianostriana.dam.realstatev2.repository.ViviendaRepository;
import com.salesianostriana.dam.realstatev2.services.base.BaseService;
import com.salesianostriana.dam.realstatev2.users.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViviendaService extends BaseService<Vivienda, Long, ViviendaRepository> {

    public List<Vivienda> findTop10ViviendaOrderByInteresas (){

        return repositorio.top5ViviendasInteresas();

    }

    public Boolean comprobacionGestora (Vivienda vivienda, @AuthenticationPrincipal User userLogged){
        Boolean comprobacion = false;
        for (User gestor : vivienda.getInmobiliaria().getGestores()) {
            if (gestor.getId().equals(userLogged.getId())) {
                comprobacion=true;

            }
        }
        return comprobacion;
    }


}
