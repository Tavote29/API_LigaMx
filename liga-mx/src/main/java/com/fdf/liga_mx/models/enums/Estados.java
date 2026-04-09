package com.fdf.liga_mx.models.enums;

public enum Estados {
    INACTIVO((short) 0),
    ACTIVO((short) 1),
    SUSPENDIDO((short) 2),
    RETIRADO((short) 3),
    CANCELADO((short) 4),
    FINALIZADO((short) 5);

    private final Short codigo;

    Estados(Short codigo) {
        this.codigo = codigo;
    }

    public Short getCodigo() {
        return codigo;
    }

    public static Estados fromCode(Short code) {
        for (Estados estados : Estados.values()) {
            if (estados.getCodigo().equals(code)) {
                return estados;
            }
        }
        throw new IllegalArgumentException("Código de estado no válido: " + code);
    }
}
