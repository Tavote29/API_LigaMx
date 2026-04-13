package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TRASPASOS")
public class Traspaso {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_TRASPASO", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLUBORIGEN")
    private Club clubOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLUBDESTINO")
    private Club clubDestino;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "FECHA_TRASPASO")
    private LocalDate fechaTraspaso;

    @Column(name = "FECHA_FIN")
    private LocalDate fechaFin;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "NUI_JUGADOR", nullable = false)
    private Jugador jugador;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_TIPO", nullable = false)
    private TipoTraspaso tipo;

}
