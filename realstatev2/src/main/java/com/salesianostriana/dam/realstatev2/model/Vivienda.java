package com.salesianostriana.dam.realstatev2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesianostriana.dam.realstatev2.users.model.User;
import com.salesianostriana.dam.realstatev2.users.model.UserRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Vivienda implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Al hacerlo en auto, peta
    private Long id;

    private String titulo, descripcion, avatar, latlng;
    private String direccion, poblacion, provincia;

    @Column(name = "codigoPostal")
    private String codigoPostal;

    @Column(name = "numHabitaciones")
    private int numHabitaciones;

    @Column(name = "numBanios")
    private int numBanios;
    private double precio;
    private double metrosCuadrados;

    @Column(name = "tipoVivienda")
    @Enumerated(EnumType.STRING)
    private Tipo tipoVivienda;

    @Column(name = "tienePiscina")
    private boolean tienePiscina;

    @Column(name = "tieneAscensor")
    private boolean tieneAscensor;

    @Column(name = "tieneGaraje")
    private boolean tieneGaraje;


    @ManyToOne
    @JoinColumn(name = "inmobiliaria_id", foreignKey = @ForeignKey(name = "FK_VIVIENDA_INMOBILIARIA"), nullable = true)
    private Inmobiliaria inmobiliaria;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_VIVIENDA_USER"))
    @JsonIgnore

    private User propietario;




    public void addPropietario(User u) {

        if(u.getRoles().equals(UserRole.PROPIETARIO)) this.propietario = u;
        if (u.getViviendas() == null)
            u.setViviendas(new ArrayList<>());
        u.getViviendas().add(this);
    }

    public void removePropietario(User u) {
        if(u.getRoles().equals(UserRole.PROPIETARIO)){
            u.getViviendas().remove(this);
            this.propietario = null;
        }

    }

    public void addInmobiliaria(Inmobiliaria i) {
        this.inmobiliaria = i;
        i.getViviendas().add(this);
    }

    public void removeInmobiliaria(Inmobiliaria i) {
        i.getViviendas().remove(this);
        this.inmobiliaria = null;
    }

}
