package com.cs.roomdbapi.converter;

import com.cs.roomdbapi.utilities.PropertyTypeSource;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PropertyTypeSourceConverter implements AttributeConverter<PropertyTypeSource, String> {

    @Override
    public String convertToDatabaseColumn(PropertyTypeSource propertyTypeSource) {
        if (propertyTypeSource == null) {
            return null;
        }
        return propertyTypeSource.getCode();
    }

    @Override
    public PropertyTypeSource convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(PropertyTypeSource.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
