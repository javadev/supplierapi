package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Language;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.LanguageMapper;
import com.cs.roomdbapi.model.LanguageEntity;
import com.cs.roomdbapi.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

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
        LanguageEntity entity = languageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, id));

        return LanguageMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Language getLanguageByCode(String code) {
        LanguageEntity entity = languageRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, CODE, code));

        return LanguageMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Language addLanguage(Language language) {
        if (languageRepository.existsByCode(language.getCode())) {
            throw new BadRequestException(String.format("Language with '%s' code already exists", language.getCode()), language);
        }

        LanguageEntity save = languageRepository.save(LanguageMapper.MAPPER.toEntity(language));
        
        return LanguageMapper.MAPPER.toDTO(save);
    }

    @Override
    public Language updateLanguage(Integer id, Language language) {
        LanguageEntity entity = languageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, id));

        if (!entity.getCode().equals(language.getCode())) {
            if (languageRepository.existsByCode(language.getCode())) {
                throw new BadRequestException(String.format("Language with '%s' code already exists", language.getCode()), language);
            }
            entity.setCode(language.getCode());
        }
        
        entity.setName(language.getName());
        entity.setNativeName(language.getNativeName());
        entity.setCode2T(language.getCode2T());
        entity.setCode2B(language.getCode2B());
        entity.setCode3(language.getCode3());

        LanguageEntity save = languageRepository.save(entity);

        return LanguageMapper.MAPPER.toDTO(save);
    }
    
}
