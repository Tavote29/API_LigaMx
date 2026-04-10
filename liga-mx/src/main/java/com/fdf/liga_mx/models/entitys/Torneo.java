package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TORNEOS")
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TORNEO", nullable = false)
    private Long id;

    @Size(max = 100)
    @Nationalized
    @Column(name = "NOMBRE_TORNEO", length = 100, nullable = false,unique = true)
    private String nombreTorneo;

    @NotNull
    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "FECHA_FIN", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "STATUS")
    private Short status;

    @OneToMany(mappedBy = "idTorneo")
    @ToString.Exclude
    private Set<Partido> partidos = new LinkedHashSet<>();


}