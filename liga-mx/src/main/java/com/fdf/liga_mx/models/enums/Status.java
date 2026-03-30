package com.fdf.liga_mx.models.enums;

public enum Status {

    ACTIVO((short) 1),
    SUSPENDIDO((short) 2),
    RETIRADO((short) 3),
    CANCELADO((short) 4);

    private final Short codigo;

    Status(Short codigo) {
        this.codigo = codigo;
    }

    public Short getCodigo() {
        return codigo;
    }

    public static Status fromCode(Short code) {
        for (Status status : Status.values()) {
            if (status.getCodigo().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de estado no válido: " + code);
    }
}
