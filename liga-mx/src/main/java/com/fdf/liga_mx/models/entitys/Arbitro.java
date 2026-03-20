package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "ARBITROS")
public class Arbitro {
    @Id
    @Column(name = "NUI_ARBITRO", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PERSONA", nullable = false)
    private Persona idPersona;

    @NotNull
    @Column(name = "FECHA_INCORPORACION", nullable = false)
    private LocalDate fechaIncorporacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria idCategoria;

    @OneToMany(mappedBy = "idArbitroCentral")
    private Set<Partido> partidosArbitoCentral = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCuartoArbitro")
    private Set<Partido> partidosCuartoArbitro = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idArbitroAsistente1")
    private Set<Partido> partidosAsistente1 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idArbitroAsistente2")
    private Set<Partido> partidosAsistente2 = new LinkedHashSet<>();


}