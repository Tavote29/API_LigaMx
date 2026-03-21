package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "DIRECTORES_TECNICOS")
public class DT {
    @Id
    @Column(name = "NUI_DT", nullable = false)
    private Long id;

    @Column(name = "TARJETAS_AMARILLAS", columnDefinition = "tinyint")
    private Short tarjetasAmarillas;

    @Column(name = "TARJETAS_ROJAS", columnDefinition = "tinyint")
    private Short tarjetasRojas;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PERSONA", nullable = false)
    private Persona idPersona;

    @OneToMany(mappedBy = "idDt")
    private Set<Club> clubs = new LinkedHashSet<>();


}