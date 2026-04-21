package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TIPOS_TRASPASOS")
public class TipoTraspaso {
    @Id
    @Column(name = "ID_TIPO")
    private Short id;

    @Column(name = "DESCRIPCION_TIPO")
    private String descripcion;

    @OneToMany(mappedBy = "tipo", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Traspaso> traspaso = new LinkedHashSet<>();
}
