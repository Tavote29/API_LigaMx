package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "PERSONAS")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_PERSONA", nullable = false)
    private UUID id;

    @Size(max = 255)
    @NotNull
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "FECHA_NACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Size(max = 255)
    @NotNull
    @Column(name = "LUGAR_NACIMIENTO", nullable = false)
    private String lugarNacimiento;

    @NotNull
    @Column(name = "ESTATURA", nullable = false, precision = 3, scale = 2)
    private BigDecimal estatura;

    @NotNull
    @Column(name = "PESO", nullable = false, precision = 4, scale = 1)
    private BigDecimal peso;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STATUS", nullable = false)
    private Status idStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_NACIONALIDAD", nullable = false)
    private Nacionalidad idNacionalidad;

    @OneToMany(mappedBy = "persona", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Arbitro> arbitros = new LinkedHashSet<>();

    @OneToMany(mappedBy = "persona", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<DT> DTS = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPersona")
    private Set<Jugador> jugadores = new LinkedHashSet<>();


}