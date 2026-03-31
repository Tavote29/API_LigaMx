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
@Table(name = "NACIONALIDADES")
public class Nacionalidad {
    @Id
    @Column(name = "ID_NACIONALIDAD", nullable = false)
    private Short id;

    @Size(max = 150)
    @NotNull
    @Column(name = "NOMBRE_NACIONALIDAD", nullable = false, length = 150)
    private String nombreNacionalidad;

    @OneToMany(mappedBy = "idNacionalidad")
    @ToString.Exclude
    private Set<Persona> personas = new LinkedHashSet<>();


}