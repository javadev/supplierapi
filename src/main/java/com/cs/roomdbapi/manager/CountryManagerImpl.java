package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Country;
import com.cs.roomdbapi.mapper.CountryMapper;
import com.cs.roomdbapi.model.CountryEntity;
import com.cs.roomdbapi.model.CountryTranslationEntity;
import com.cs.roomdbapi.repository.CountryRepository;
import com.cs.roomdbapi.repository.TranslationRepository;
import com.cs.roomdbapi.utilities.AppUtils;
import com.cs.roomdbapi.model.CountryCodeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryManagerImpl implements CountryManager {

    private final CountryRepository countryRepository;

    private final TranslationRepository translationRepository;

    @Override
    public List<Country> getCountriesByFormat(CountryCodeFormat format, String langCode) {
        List<CountryEntity> all = countryRepository.findAll();

        Map<Integer, CountryTranslationEntity> translationsForLang = getTranslationsForLang(langCode);
        Map<Integer, CountryTranslationEntity> translationsDefaultLang = getTranslationsForDefaultLang();

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

            setNamesForLanguage(country, translationsDefaultLang.get(country.getId()), translationsForLang.get(country.getId()));
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

            CountryTranslationEntity translation = translationRepository.findByLanguage_CodeAndCountryId(langCode, id);
            CountryTranslationEntity defaultTranslation = translationRepository.findByLanguage_CodeAndCountryId(AppUtils.DEFAULT_LANGUAGE_CODE, id);

            setNamesForLanguage(country, defaultTranslation, translation);
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

            CountryTranslationEntity translation = translationRepository.findByLanguage_CodeAndCountryId(langCode, country.getId());
            CountryTranslationEntity defaultTranslation = translationRepository.findByLanguage_CodeAndCountryId(AppUtils.DEFAULT_LANGUAGE_CODE, country.getId());

            setNamesForLanguage(country, defaultTranslation, translation);
        }

        return country;
    }

    private Map<Integer, CountryTranslationEntity> getTranslationsForLang(String langCode) {
        List<CountryTranslationEntity> translationsForLangList = translationRepository.findAllByLanguage_Code(langCode);
        return translationsForLangList
                .stream()
                .collect(Collectors.toMap(CountryTranslationEntity::getCountryId, Function.identity()));
    }

    private Map<Integer, CountryTranslationEntity> getTranslationsForDefaultLang() {
        List<CountryTranslationEntity> translationsDefaultLangList = translationRepository.findAllByLanguage_Code(AppUtils.DEFAULT_LANGUAGE_CODE);
        return translationsDefaultLangList
                .stream()
                .collect(Collectors.toMap(CountryTranslationEntity::getCountryId, Function.identity()));
    }

    private void setNamesForLanguage(Country country, CountryTranslationEntity defaultTranslation, CountryTranslationEntity langTranslation) {
        String name = null;
        String fullName = null;

        if (langTranslation != null) {
            if (!langTranslation.getName().isBlank()) {
                name = langTranslation.getName();
            }
            if (!langTranslation.getFullName().isBlank()) {
                fullName = langTranslation.getFullName();
            }
        }

        if (defaultTranslation != null) {
            if (!defaultTranslation.getName().isBlank() && name == null) {
                name = defaultTranslation.getName();
            }
            if (!defaultTranslation.getFullName().isBlank() && fullName == null) {
                fullName = defaultTranslation.getFullName();
            }
        }

        country.setName(name);
        country.setFullName(fullName);
    }
}
