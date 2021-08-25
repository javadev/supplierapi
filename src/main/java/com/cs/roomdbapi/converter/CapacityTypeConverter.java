package com.cs.roomdbapi.converter;

import com.cs.roomdbapi.model.CapacityType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CapacityTypeConverter implements AttributeConverter<CapacityType, String> {

    @Override
    public String convertToDatabaseColumn(CapacityType capacityType) {
        if (capacityType == null) {
            return null;
        }
        return capacityType.getCode();
    }

    @Override
    public CapacityType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(CapacityType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
