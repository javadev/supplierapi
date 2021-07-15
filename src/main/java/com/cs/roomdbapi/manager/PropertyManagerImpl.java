package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Property;
import com.cs.roomdbapi.dto.PropertySaveRequest;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.PropertyMapper;
import com.cs.roomdbapi.model.CurrencyEntity;
import com.cs.roomdbapi.model.PropertyEntity;
import com.cs.roomdbapi.model.SupplierEntity;
import com.cs.roomdbapi.repository.CurrencyRepository;
import com.cs.roomdbapi.repository.PropertyRepository;
import com.cs.roomdbapi.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyManagerImpl implements PropertyManager {

    private final PropertyRepository propertyRepository;

    private final SupplierRepository supplierRepository;

    private final CurrencyRepository currencyRepository;

    @Override
    public List<Property> getProperties() {
        List<PropertyEntity> all = propertyRepository.findAll();

        return PropertyMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<Property> getPropertiesBySupplier(String supplierName) {
        SupplierEntity supplier = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

        List<PropertyEntity> all = propertyRepository.findAllBySupplierIs(supplier);

        return PropertyMapper.MAPPER.toListDTOWithoutSupplier(all);
    }

    @Override
    public Property getPropertyById(Integer id) {
        PropertyEntity entity = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, id));

        return PropertyMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Property getPropertyBySupplierPropertyId(String id) {
        PropertyEntity entity = propertyRepository.findBySupplierPropertyId(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, SUPPLIER_PROPERTY_ID, id));

        return PropertyMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Property addProperty(PropertySaveRequest property, String supplierName) {
        if (propertyRepository.existsBySupplierPropertyId(property.getSupplierPropertyId())) {
            throw new BadRequestException(String.format("Property with '%s' Supplier Property Id already exists", property.getSupplierPropertyId()), property);
        }

        SupplierEntity supplier = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

        CurrencyEntity currencyEntity = null;
        if (property.getHomeCurrencyId() != null) {
            Integer currencyId = property.getHomeCurrencyId();
            currencyEntity = currencyRepository.findById(currencyId)
                    .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, ID, currencyId));
        }

        PropertyEntity entity = PropertyMapper.MAPPER.saveRequestToEntity(property);
        entity.setHomeCurrency(currencyEntity);
        entity.setSupplier(supplier);

        PropertyEntity save = propertyRepository.save(entity);
        log.info("Property added: '{}'", save.toString());

        return PropertyMapper.MAPPER.toDTO(save);
    }

    @Override
    public Property updateProperty(Integer id, PropertySaveRequest property, String supplierName) {
        PropertyEntity entity = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, id));

        if (propertyRepository.existsBySupplierPropertyId(property.getSupplierPropertyId())) {
            throw new BadRequestException(String.format("Property with '%s' Supplier Property Id already exists", property.getSupplierPropertyId()), property);
        }

        CurrencyEntity currencyEntity = null;
        if (property.getHomeCurrencyId() != null) {
            Integer currencyId = property.getHomeCurrencyId();
            currencyEntity = currencyRepository.findById(currencyId)
                    .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, ID, currencyId));
        }

        entity.setHomeCurrency(currencyEntity);
        entity.setSupplierPropertyId(property.getSupplierPropertyId());
        entity.setCode(property.getCode());
        entity.setName(property.getName());
        entity.setAlternativeName(property.getAlternativeName());
        entity.setStatus(property.getStatus());
        entity.setForTesting(property.getForTesting());

        PropertyEntity save = propertyRepository.save(entity);
        log.info("Property with id '{}' updated to: '{}'", entity.getId(), entity.toString());

        return PropertyMapper.MAPPER.toDTO(save);
    }

}
