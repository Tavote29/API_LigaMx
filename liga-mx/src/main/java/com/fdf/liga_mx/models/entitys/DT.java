package com.fdf.liga_mx.models.entitys;

import com.fdf.liga_mx.models.enums.Estados;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@ToString
@Entity
@Table(name = "DIRECTORES_TECNICOS")
public class DT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUI_DT", nullable = false)
    private Long id;

    @Column(name = "TARJETAS_AMARILLAS", columnDefinition = "tinyint")
    private Short tarjetasAmarillas;

    @Column(name = "TARJETAS_ROJAS", columnDefinition = "tinyint")
    private Short tarjetasRojas;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_PERSONA", nullable = false)
    private Persona persona;

    @OneToOne(mappedBy = "idDt", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Club club;

    @Column(name = "ID_STATUS", nullable = true)
    private Short status;

    @PrePersist
    public void prePersist(){
        this.status = Estados.ACTIVO.getCodigo();
    }
}