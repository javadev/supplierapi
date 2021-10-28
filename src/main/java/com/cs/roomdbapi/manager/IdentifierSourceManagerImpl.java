package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.IdentifierSource;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.IdentifierSourceMapper;
import com.cs.roomdbapi.model.IdentifierSourceEntity;
import com.cs.roomdbapi.repository.IdentifierSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
public class IdentifierSourceManagerImpl implements IdentifierSourceManager {

    private final IdentifierSourceRepository identifierSourceRepository;

    @Override
    public List<IdentifierSource> getAll() {
        List<IdentifierSourceEntity> all = identifierSourceRepository.findAll();

        return IdentifierSourceMapper.MAPPER.toListDTO(all);
    }

    @Override
    public IdentifierSource getById(Integer id) {
        IdentifierSourceEntity entity = identifierSourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ID, id));

        return IdentifierSourceMapper.MAPPER.toDTO(entity);
    }

    @Override
    public IdentifierSource getByAbbreviation(String abbreviation) {
        IdentifierSourceEntity entity = identifierSourceRepository.findByAbbreviation(abbreviation)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ABBREVIATION, abbreviation));

        return IdentifierSourceMapper.MAPPER.toDTO(entity);
    }

}
