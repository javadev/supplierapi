package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Language;

import java.util.List;

public interface TranslationManager {

    List<Language> getLanguages();

    Language getLanguageById(Integer id);

    Language getLanguageByCode(String code);
}
