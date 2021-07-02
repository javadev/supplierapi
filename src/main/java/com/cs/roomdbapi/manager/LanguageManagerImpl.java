package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Language;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.mapper.LanguageMapper;
import com.cs.roomdbapi.model.LanguageEntity;
import com.cs.roomdbapi.repository.LanguageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LanguageManagerImpl implements LanguageManager {

    private final LanguageRepository languageRepository;

    @Override
    public List<Language> getLanguages() {
        List<LanguageEntity> all = languageRepository.findAll();

        return LanguageMapper.MAPPER.toListDTO(all);
    }

    @Override
    public Language getLanguageById(Integer id) {
        Optional<LanguageEntity> findResult = languageRepository.findById(id);

        return getFromOptional(findResult);
    }

    @Override
    public Language getLanguageByCode(String code) {
        Optional<LanguageEntity> findResult = languageRepository.findByCode(code);

        return getFromOptional(findResult);
    }

    private Language getFromOptional(@NonNull Optional<LanguageEntity> findResult) {
        Language result = null;
        if (findResult.isPresent()) {
            result = LanguageMapper.MAPPER.toDTO(findResult.get());
        }

        return result;
    }

    @Override
    public Language addLanguage(Language language) {
        if (languageRepository.existsByCode(language.getCode())) {
            throw new BadRequestException("Language with such code already exists", language);
        }

        LanguageEntity save = languageRepository.save(LanguageMapper.MAPPER.toEntity(language));
        
        return LanguageMapper.MAPPER.toDTO(save);
    }
    
}
