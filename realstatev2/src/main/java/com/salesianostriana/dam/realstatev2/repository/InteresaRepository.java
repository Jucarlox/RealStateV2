package com.salesianostriana.dam.realstatev2.repository;

import com.salesianostriana.dam.realstatev2.model.Interesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InteresaRepository extends JpaRepository<Interesa,Long> {

    @Query(value = """
            SELECT * FROM Interesa int 
            WHERE int.interesado_id IN
            (SELECT interesado_id FROM Interesa i
            GROUP BY interesado_id)
            """, nativeQuery = true)
    List<Interesa> findInteresados();
}
