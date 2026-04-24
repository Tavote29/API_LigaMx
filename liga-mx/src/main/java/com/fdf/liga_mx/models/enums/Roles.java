package com.fdf.liga_mx.models.enums;

public enum Roles {

SUPER_ADMIN((short) 1),
BASICO((short) 2);

    private final Short codigo;

    Roles(Short codigo) {
        this.codigo = codigo;
    }

    public Short getCodigo() {
        return codigo;
    }

    public static Roles fromCode(Short code) {
        for (Roles role: Roles.values()) {
            if (role.getCodigo().equals(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("error.enum.estado_invalido");
    }

}
