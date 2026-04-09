package com.fdf.liga_mx.models.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RolesConverter implements AttributeConverter<Roles, Short> {

    @Override
    public Short convertToDatabaseColumn(Roles roles) {
        if (roles == null) {
            return null;
        }
        return roles.getCodigo();
    }

    @Override
    public Roles convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        return Roles.fromCode(dbData);
    }
}
