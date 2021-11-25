package com.salesianostriana.dam.realstatev2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesianostriana.dam.realstatev2.users.model.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Interesa implements Serializable {
    @Builder.Default
    @EmbeddedId
    private InteresaPK id = new InteresaPK();

    @JsonIgnore
    @ManyToOne
    @MapsId("vivienda_id")
    @JoinColumn(name="vivienda_id", foreignKey = @ForeignKey(name = "FK_INTERESA_VIVIENDA"))
    private Vivienda vivienda;

    @JsonIgnore
    @ManyToOne
    @MapsId("interesado_id")
    @JoinColumn(name="interesado_id", foreignKey = @ForeignKey(name = "FK_INTERESA_INTERESADO"))
    private User interesado;


    @CreatedDate
    private LocalDateTime createdDate = LocalDateTime.now();
    private String mensaje;



    public void addInteresado(User i) {
        this.interesado = i;
        i.getInteresas().add(this);
    }

    public void removeInteresado(User i) {
        i.getInteresas().remove(this);
        this.interesado = null;
    }

    public void addVivienda(Vivienda v) {
        this.vivienda = v;
        v.getInteresas().add(this);
    }

    public void removeVivienda(Vivienda v) {
        v.getInteresas().remove(this);
        this.vivienda = null;

    }
}
