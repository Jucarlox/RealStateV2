package com.salesianostriana.dam.realstatev2.services;

import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.repository.InmobiliariaRepository;
import com.salesianostriana.dam.realstatev2.services.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class InmobiliariaService extends BaseService<Inmobiliaria, Long, InmobiliariaRepository> {

    public Boolean findGestorOfViviendaId ( Inmobiliaria inmobiliaria){

        return repositorio.findByGestoresId(inmobiliaria);

    }
}
