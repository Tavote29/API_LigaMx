package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "TIPOS_ACONTECIMIENTOS")
public class TiposAcontecimiento {
    @Id
    @Column(name = "ID_TIPO",nullable = false)
    private Short id;

    @Size(max = 50)
    @NotNull
    @Column(name = "DESCRIPCION_TIPO", nullable = false, length = 50)
    private String descripcionTipo;

    @OneToMany(mappedBy = "idTipo")
    private Set<Acontecimiento> acontecimientos = new LinkedHashSet<>();


}