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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private Estado idEstado;

    @ToString.Exclude
    @OneToMany(mappedBy = "idCiudad")
    private Set<Club> clubs = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "idCiudad")
    private Set<Estadio> estadios = new LinkedHashSet<>();


}