package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "CIUDADES")
public class Ciudad {
    @Id
    @Column(name = "ID_CIUDAD", nullable = false)
    private Short id;

    @Size(max = 200)
    @NotNull
    @Column(name = "NOMBRE_CIUDAD", nullable = false, length = 200)
    private String nombreCiudad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private Estado idEstado;

    @OneToMany(mappedBy = "idCiudad")
    private Set<Club> clubs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCiudad")
    private Set<Estadio> estadios = new LinkedHashSet<>();


}