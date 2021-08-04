package com.cs.roomdbapi.converter;

import com.cs.roomdbapi.model.CodeSource;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CodeSourceConverter implements AttributeConverter<CodeSource, String> {

    @Override
    public String convertToDatabaseColumn(CodeSource codeSource) {
        if (codeSource == null) {
            return null;
        }
        return codeSource.getCode();
    }

    @Override
    public CodeSource convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(CodeSource.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
