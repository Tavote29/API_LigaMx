package com.fdf.liga_mx.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ROLES", schema = "dbo")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "tinyint not null")
    private Short id;

    @Size(max = 100)
    @NotNull
    @Column(name = "ROL_NAME", nullable = false, length = 100)
    private String rolName;

    @Column(name = "ID_STATUS", columnDefinition = "tinyint not null")
    private Short idStatus;

    @OneToMany(mappedBy = "role")
    private Set<Usuario> usuarios = new LinkedHashSet<>();


}