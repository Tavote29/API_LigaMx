package com.fdf.liga_mx.models.enums;

public enum TiposAcontecimientos {
    GOL((short) 1),
    CAMBIO((short) 2),
    PENAL((short) 3),
    TARJETA_AMARILLA((short) 4),
    TARJETA_ROJA((short) 5),
    CORNER((short) 6),
    TIRO_LIBRE((short) 7),
    FALTA((short) 8),
    AUTOGOL((short) 9),
    INICIO_PARTIDO((short) 10),
    FIN_PARTIDO((short) 11);

    private final Short codigo;

    TiposAcontecimientos(Short codigo) {
        this.codigo = codigo;
    }

    public Short getCodigo() {
        return codigo;
    }

    public static TiposAcontecimientos fromCode(Short code) {
        for (TiposAcontecimientos tipo : TiposAcontecimientos.values()) {
            if (tipo.getCodigo().equals(code)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo de acontecimiento no válido: " + code);
    }
}
