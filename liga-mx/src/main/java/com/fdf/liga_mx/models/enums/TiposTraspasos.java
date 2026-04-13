package com.fdf.liga_mx.models.enums;

public enum TiposTraspasos {
    VENTA_DEFINITIVA((short)1),
    CESION((short)2),
    PRESTAMO_SIN_OPCION_A_COMPRA((short)3),
    PRESTAMO_CON_OPCION_A_COMPRA((short)4),
    RESCISION((short) 5),
    FINALIZACION_CONTRATO((short)6)
    ;

    private final Short codigo;

    TiposTraspasos(Short codigo){this.codigo = codigo;}

    public Short getCodigo() {
        return codigo;
    }

    public static TiposTraspasos fromCode(short code){
        for (TiposTraspasos tiposTraspasos: TiposTraspasos.values()){
            if (tiposTraspasos.getCodigo().equals(code)){
                return tiposTraspasos;
            }
        }
        throw  new IllegalArgumentException("Codigo no valido: " + code);
    }
}
