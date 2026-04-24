package com.fdf.liga_mx.models.entitys;


import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.models.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USUARIOS", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "USUARIOS_EMAIL_UQ",
                columnNames = {"EMAIL"}),
        @UniqueConstraint(name = "USUARIOS_USERNAME_UQ",
                columnNames = {"USERNAME"})})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Size(max = 254)
    @NotNull
    @Column(name = "EMAIL", nullable = false, length = 254)
    private String email;

    @Size(max = 50)
    @NotNull
    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @NotNull
    @Column(name = "ROLE_ID", nullable = false)
    private Roles role;

    @Column(name = "ID_STATUS", columnDefinition = "tinyint")
    private Short idStatus;

    @PrePersist
    public void prePersist(){
        this.idStatus = Estados.ACTIVO.getCodigo();
    }


}