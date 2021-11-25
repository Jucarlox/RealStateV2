package com.salesianostriana.dam.realstatev2.services;

import com.salesianostriana.dam.realstatev2.model.Interesa;
import com.salesianostriana.dam.realstatev2.repository.InteresaRepository;
import com.salesianostriana.dam.realstatev2.services.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteresaService extends BaseService<Interesa,Long, InteresaRepository> {

    public List<Interesa> findInteresados (){

        return repositorio.findInteresados();

    }
}
