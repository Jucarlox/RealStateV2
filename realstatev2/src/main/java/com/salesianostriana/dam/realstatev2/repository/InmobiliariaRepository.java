package com.salesianostriana.dam.realstatev2.repository;

import com.salesianostriana.dam.realstatev2.model.Inmobiliaria;
import com.salesianostriana.dam.realstatev2.model.Vivienda;
import com.salesianostriana.dam.realstatev2.users.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InmobiliariaRepository extends JpaRepository<Inmobiliaria, Long> {



    @Query(value = """
            SELECT  FROM Inmobiliaria i
            WHERE i.gestores IN (SELECT i1.id 
            FROM Inmobiliaria i1 JOIN User u ON i1.gestores.id=u.id
            GROUP BY i1.id
            )
            """, nativeQuery = true)
    Boolean findByGestoresId(Inmobiliaria inmobiliaria);


}
