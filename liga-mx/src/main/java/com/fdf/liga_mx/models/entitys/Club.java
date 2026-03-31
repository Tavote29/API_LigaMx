package com.fdf.liga_mx.models.entitys;

import com.fdf.liga_mx.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CLUBES")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLUB", nullable = false)
    private Short id;

    @Column(name = "NOMBRE_CLUB", nullable = false)
    private String nombreClub;


    @Column(name = "FECHA_FUNDACION", nullable = false)
    private LocalDate fechaFundacion;

    @Size(max = 255)
    @Column(name = "PROPIETARIO", nullable = false)
    private String propietario;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADO", nullable = false)
    private Estado idEstado;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CIUDAD", nullable = false)
    private Ciudad idCiudad;


    @OneToOne
    @JoinColumn(name = "ID_DT", nullable = false,unique = true)
    private DT idDt;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTADIO", nullable = false)
    private Estadio idEstadio;

    @Column(name = "ID_STATUS", nullable = true)
    private Short status;

    @OneToMany(mappedBy = "idClub")
    private Set<Jugador> jugadores = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idLocal")
    private Set<Partido> partidosLocal = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idVisitante")
    private Set<Partido> partidosVisitante = new LinkedHashSet<>();

    @PrePersist
    public void prePersist(){
        this.status = Status.ACTIVO.getCodigo();
    }



}