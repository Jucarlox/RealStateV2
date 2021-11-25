package com.salesianostriana.dam.realstatev2.services;

import com.salesianostriana.dam.realstatev2.model.Vivienda;
import com.salesianostriana.dam.realstatev2.repository.ViviendaRepository;
import com.salesianostriana.dam.realstatev2.services.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViviendaService extends BaseService<Vivienda, Long, ViviendaRepository> {

    public List<Vivienda> findTop10ViviendaOrderByInteresas (){

        return repositorio.top5ViviendasInteresas();

    }
}
