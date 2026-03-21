package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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

    @ToString.Exclude
    @OneToMany(mappedBy = "idEstado")
    private Set<Ciudad> ciudades = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "idEstado")
    private Set<Club> clubs = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "idEstado")
    private Set<Estadio> estadios = new LinkedHashSet<>();


}