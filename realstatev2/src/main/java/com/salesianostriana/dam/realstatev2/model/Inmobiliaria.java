package com.salesianostriana.dam.realstatev2.model;

import com.salesianostriana.dam.realstatev2.users.model.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = Inmobiliaria.INMOBILIARIA_CON_VIVIENDA,
        attributeNodes = {
                @NamedAttributeNode("viviendas")
        }
)


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Inmobiliaria implements Serializable {
        public static final String INMOBILIARIA_CON_VIVIENDA= "grafo-inmobiliaria-con-vivienda";
        //public static final String INMOBILIARIA_CON_USER= "grafo-inmobiliaria-con-user";
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String nombre;
        private String email;
        private String telefono;

        @Builder.Default
        @OneToMany(mappedBy = "inmobiliaria", fetch = FetchType.LAZY)

        private List<Vivienda> viviendas=new ArrayList<>();

        @Builder.Default
        @OneToMany(mappedBy = "inmobiliaria",fetch = FetchType.EAGER)

        private List<User> gestores=new ArrayList<>();

}
