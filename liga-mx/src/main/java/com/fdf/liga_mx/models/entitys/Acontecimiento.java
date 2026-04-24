package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "ACONTECIMIENTOS")
public class Acontecimiento {
    @Id
    @ColumnDefault("newsequentialid()")
    @org.hibernate.annotations.UuidGenerator(style = org.hibernate.annotations.UuidGenerator.Style.TIME)
    @Column(name = "ID_ACONTECIMIENTO", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_TIPO", nullable = false)
    private TiposAcontecimiento idTipo;

    @Size(max = 10)
    @Column(name = "MINUTO", nullable = false, length = 10)
    private String minuto;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_JUGADOR_PRIMARIO", nullable = true)
    private Jugador idJugadorPrimario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_JUGADOR_SECUNDARIO", nullable = true)
    private Jugador idJugadorSecundario;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PARTIDO", nullable = false)
    private Partido idPartido;


}