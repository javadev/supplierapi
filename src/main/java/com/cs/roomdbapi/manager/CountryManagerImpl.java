package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Country;
import com.cs.roomdbapi.mapper.CountryMapper;
import com.cs.roomdbapi.model.CountryEntity;
import com.cs.roomdbapi.model.CountryTranslationEntity;
import com.cs.roomdbapi.repository.CountryRepository;
import com.cs.roomdbapi.utilities.AppUtils;
import com.cs.roomdbapi.utilities.CountryCodeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryManagerImpl implements CountryManager {

    private final CountryRepository countryRepository;

    @Override
    public List<Country> getCountriesByFormat(CountryCodeFormat format, String langCode) {
        List<CountryEntity> all = countryRepository.findAll();

        List<Country> countries = new ArrayList<>();
        for (CountryEntity entity : all) {
            Country country;

            switch (format) {
                case ALL:
                    country = CountryMapper.MAPPER.toDTO(entity);
                    break;
                case ALPHA3:
                    country = CountryMapper.MAPPER.toDTOWithCodeA3(entity);
                    break;
                case NUMERIC:
                    country = CountryMapper.MAPPER.toDTOWithNumeric(entity);
                    break;
                default: // ALPHA2 is a default output
                    country = CountryMapper.MAPPER.toDTOWithCode(entity);
            }

            setNamesForLanguage(langCode, country, entity);
            countries.add(country);
        }

        return countries;
    }

    @Override
    public Country getCountryById(Integer id, String langCode) {
        Optional<CountryEntity> countryEntity = countryRepository.findById(id);

        Country country = null;
        if (countryEntity.isPresent()) {
            CountryEntity entity = countryEntity.get();
            country = CountryMapper.MAPPER.toDTO(entity);

            setNamesForLanguage(langCode, country, entity);
        }

        return country;
    }

    @Override
    public Country getCountryByCode(String code, String langCode) {
        Optional<CountryEntity> countryEntity = countryRepository.findByCode(code.toUpperCase());

        Country country = null;
        if (countryEntity.isPresent()) {
            CountryEntity entity = countryEntity.get();
            country = CountryMapper.MAPPER.toDTO(entity);

            setNamesForLanguage(langCode, country, entity);
        }

        return country;
    }

    private void setNamesForLanguage(String langCode, Country country, CountryEntity entity) {
        String name = "";
        String fullName = "";
        String defaultName = null;
        String defaultFullName = null;

        List<CountryTranslationEntity> translations = entity.getTranslations();
        for (CountryTranslationEntity translation : translations) {
            if (translation.getLanguage() != null) {
                String code = translation.getLanguage().getCode();
                if (code != null) {
                    if (AppUtils.DEFAULT_LANGUAGE_CODE.equalsIgnoreCase(code)) {
                        defaultName = translation.getName();
                        defaultFullName = translation.getFullName();
                    } else if (code.equalsIgnoreCase(langCode)) {
                        name = translation.getName();
                        fullName = translation.getFullName();
                        break;
                    }
                }
            }
        }

        country.setName(name.isBlank() ? defaultName : name);
        country.setFullName(fullName.isBlank() ? defaultFullName : fullName);
    }
}
