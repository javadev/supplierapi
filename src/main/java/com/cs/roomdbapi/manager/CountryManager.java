package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Country;
import com.cs.roomdbapi.model.CountryCodeFormat;

import java.util.List;

public interface CountryManager {

    List<Country> getCountriesByFormat(CountryCodeFormat format, String langCode);

    Country getCountryById(Integer id, String langCode);

    Country getCountryByCode(String code, String langCode);

}
