package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "PARTIDOS")
public class Partido {
    @Id
    @ColumnDefault("newsequentialid()")
    @Column(name = "ID_PARTIDO", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_LOCAL", nullable = false)
    private Club idLocal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_VISITANTE", nullable = false)
    private Club idVisitante;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADIO", nullable = false)
    private Estadio idEstadio;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ARBITRO_CENTRAL", nullable = false)
    private Arbitro idArbitroCentral;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CUARTO_ARBITRO")
    private Arbitro idCuartoArbitro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ARBITRO_ASISTENTE1")
    private Arbitro idArbitroAsistente1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ARBITRO_ASISTENTE2")
    private Arbitro idArbitroAsistente2;

    @NotNull
    @Column(name = "FECHA", nullable = false)
    private Instant fecha;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STATUS", nullable = false)
    private Status idStatus;

    @OneToMany(mappedBy = "idPartido")
    @ToString.Exclude
    private Set<Acontecimiento> acontecimientos = new LinkedHashSet<>();


}