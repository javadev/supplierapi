package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Language;

import java.util.List;

public interface LanguageManager {

    List<Language> getLanguages();

    Language getLanguageById(Integer id);

    Language getLanguageByCode(String code);

    Language addLanguage(Language language);
}
