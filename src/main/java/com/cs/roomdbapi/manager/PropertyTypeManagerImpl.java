package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.PropertyType;
import com.cs.roomdbapi.mapper.PropertyTypeMapper;
import com.cs.roomdbapi.model.PropertyTypeEntity;
import com.cs.roomdbapi.repository.PropertyTypeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyTypeManagerImpl implements PropertyTypeManager {

    private final PropertyTypeRepository propertyTypeRepository;

    @Override
    public List<PropertyType> getPropertyTypes() {
        List<PropertyTypeEntity> all = propertyTypeRepository.findAll();

        return PropertyTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public PropertyType getPropertyTypeById(Integer id) {
        Optional<PropertyTypeEntity> findResult = propertyTypeRepository.findById(id);

        return getFromOptional(findResult);
    }

    @Override
    public PropertyType getPropertyTypeByCode(String code) {
        Optional<PropertyTypeEntity> findResult = propertyTypeRepository.findByCode(code);

        return getFromOptional(findResult);
    }

    private PropertyType getFromOptional(@NonNull Optional<PropertyTypeEntity> findResult) {
        PropertyType result = null;
        if (findResult.isPresent()) {
            result = PropertyTypeMapper.MAPPER.toDTO(findResult.get());
        }

        return result;
    }
}
