package com.salesianostriana.dam.realstatev2.repository;


import com.salesianostriana.dam.realstatev2.model.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViviendaRepository extends JpaRepository<Vivienda, Long> {

    @Query(value = """
            SELECT * FROM Vivienda v
            WHERE v.id IN (SELECT v1.id 
            FROM Vivienda v1 JOIN Interesa i ON v1.id=i.vivienda_id
            GROUP BY v1.id
            ORDER BY COUNT(*) DESC
            LIMIT 5)
            """, nativeQuery = true)
    List<Vivienda> top5ViviendasInteresas();
}
