package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ARBITROS")
public class Arbitro {
    @Id
    @Column(name = "NUI_ARBITRO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_PERSONA", nullable = false)
    private Persona persona;

    @NotNull
    @Column(name = "FECHA_INCORPORACION", nullable = false)
    private LocalDate fechaIncorporacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private CategoriaArbitro idCategoriaArbitro;

    @OneToMany(mappedBy = "idArbitroCentral")
    @ToString.Exclude
    private Set<Partido> partidosArbitoCentral = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCuartoArbitro")
    @ToString.Exclude
    private Set<Partido> partidosCuartoArbitro = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idArbitroAsistente1")
    @ToString.Exclude
    private Set<Partido> partidosAsistente1 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idArbitroAsistente2")
    @ToString.Exclude
    private Set<Partido> partidosAsistente2 = new LinkedHashSet<>();


}