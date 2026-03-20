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
    private Set<Partido> partidos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idStatus")
    private Set<Persona> personas = new LinkedHashSet<>();


}