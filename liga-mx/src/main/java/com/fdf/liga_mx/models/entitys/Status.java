package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "STATUS")
public class Status {
    @Id
    @Column(name = "ID_STATUS", nullable = false)
    private Short id;

    @Size(max = 100)
    @NotNull
    @Column(name = "DESCRIPCION_STATUS", nullable = false, length = 100)
    private String descripcionStatus;

    @OneToMany(mappedBy = "idStatus")
    @ToString.Exclude
    private Set<Partido> partidos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idStatus")
    @ToString.Exclude
    private Set<Persona> personas = new LinkedHashSet<>();


}