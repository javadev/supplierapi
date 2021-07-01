package com.cs.roomdbapi.converter;

import com.cs.roomdbapi.model.RoleName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleNameConverter implements AttributeConverter<RoleName, String> {

    @Override
    public String convertToDatabaseColumn(RoleName roleName) {
        if (roleName == null) {
            return null;
        }
        return roleName.getCode();
    }

    @Override
    public RoleName convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RoleName.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
