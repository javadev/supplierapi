package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.PropertyType;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.PropertyTypeMapper;
import com.cs.roomdbapi.model.PropertyTypeEntity;
import com.cs.roomdbapi.repository.PropertyTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

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
        PropertyTypeEntity entity = propertyTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_TYPE, ID, id));

        return PropertyTypeMapper.MAPPER.toDTO(entity);
    }

    @Override
    public PropertyType getPropertyTypeByCode(String code) {
        PropertyTypeEntity entity = propertyTypeRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_TYPE, CODE, code));

        return PropertyTypeMapper.MAPPER.toDTO(entity);
    }

}
