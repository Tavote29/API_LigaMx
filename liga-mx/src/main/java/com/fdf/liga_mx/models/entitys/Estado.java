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
@Table(name = "ESTADOS")
public class Estado {
    @Id
    @Column(name = "ID_ESTADO", nullable = false)
    private Short id;

    @Size(max = 150)
    @NotNull
    @Column(name = "NOMBRE_ESTADO", nullable = false, length = 150)
    private String nombreEstado;

    @OneToMany(mappedBy = "idEstado")
    private Set<Ciudad> ciudades = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idEstado")
    private Set<Club> clubs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idEstado")
    private Set<Estadio> estadios = new LinkedHashSet<>();


}