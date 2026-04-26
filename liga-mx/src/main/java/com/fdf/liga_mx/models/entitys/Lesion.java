package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LESIONES")
public class Lesion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_LESION")
    private UUID id;

    @NotNull
    @Column(name = "DESCRIPCION_LESION")
    private String descripcionLesion;

    @NotNull
    @Column(name = "FECHA_LESION")
    private LocalDateTime fechaLesion;

    @NotNull
    @Column(name = "FECHA_RECUPERACION")
    private LocalDateTime fechaRecuperacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NUI_JUGADOR", nullable = false)
    private Jugador jugador;

}
