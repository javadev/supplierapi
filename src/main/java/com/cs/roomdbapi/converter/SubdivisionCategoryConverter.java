package com.cs.roomdbapi.converter;

import com.cs.roomdbapi.utilities.SubdivisionCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SubdivisionCategoryConverter implements AttributeConverter<SubdivisionCategory, String> {

    @Override
    public String convertToDatabaseColumn(SubdivisionCategory subdivisionCategory) {
        if (subdivisionCategory == null) {
            return null;
        }
        return subdivisionCategory.getCode();
    }

    @Override
    public SubdivisionCategory convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(SubdivisionCategory.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
