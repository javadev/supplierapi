package com.cs.roomdbapi.converter;

import com.cs.roomdbapi.utilities.CountryCodeFormat;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, CountryCodeFormat> {

    @Override
    public CountryCodeFormat convert(String source) {
        return CountryCodeFormat.valueOf(source.toUpperCase());
    }

}
