package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "CATEGORIAS")
public class Categoria {
    @Id
    @Column(name = "ID_CATEGORIA", nullable = false)
    private Short id;

    @Size(max = 50)
    @NotNull
    @Column(name = "DESCRIPCION_CATEGORIA", nullable = false, length = 50)
    private String descripcionCategoria;

    @OneToMany(mappedBy = "idCategoria")
    private Set<Arbitro> arbitros = new LinkedHashSet<>();


}