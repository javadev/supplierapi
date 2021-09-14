package com.cs.roomdbapi.converter;

import com.cs.roomdbapi.model.TimeType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TimeTypeConverter implements AttributeConverter<TimeType, String> {

    @Override
    public String convertToDatabaseColumn(TimeType timeType) {
        if (timeType == null) {
            return null;
        }
        return timeType.getCode();
    }

    @Override
    public TimeType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(TimeType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
