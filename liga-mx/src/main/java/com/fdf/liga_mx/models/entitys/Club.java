package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CLUBES")
public class Club {
    @Id
    @Column(name = "ID_CLUB", nullable = false)
    private Short id;

    @Column(name = "NOMBRE_CLUB", nullable = false)
    private Short nombreClub;

    @NotNull
    @Column(name = "FECHA_FUNDACION", nullable = false)
    private LocalDate fechaFundacion;

    @Size(max = 255)
    @NotNull
    @Column(name = "PROPIETARIO", nullable = false)
    private String propietario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private Estado idEstado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CIUDAD", nullable = false)
    private Ciudad idCiudad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_DT", nullable = false)
    private DT idDt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADIO", nullable = false)
    private Estadio idEstadio;

    @OneToMany(mappedBy = "idClub")
    private Set<Jugador> jugadores = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idLocal")
    private Set<Partido> partidosLocal = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idVisitante")
    private Set<Partido> partidosVisitante = new LinkedHashSet<>();


}