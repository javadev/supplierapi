package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.*;
import com.cs.roomdbapi.model.*;
import com.cs.roomdbapi.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyManagerImpl implements PropertyManager {

    private final PropertyRepository propertyRepository;

    private final PropertyInfoRepository propertyInfoRepository;

    private final PropertyInfoLanguageRepository propertyInfoLanguageRepository;

    private final SupplierRepository supplierRepository;

    private final CurrencyRepository currencyRepository;

    private final BrandRepository brandRepository;

    private final PropertyTypeRepository propertyTypeRepository;

    private final GeoCodeRepository geoCodeRepository;

    private final LanguageRepository languageRepository;

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
    public Supplier getSupplierByPropertyId(Integer propertyId) {
        PropertyEntity entity = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        return SupplierMapper.MAPPER.toDTO(entity.getSupplier());
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

    @Override
    public PropertyInfo getPropertyInfoByPropertyId(Integer propertyId) {
        PropertyInfoEntity entity = propertyInfoRepository.findByPropertyId(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_INFO, PROPERTY_ID, propertyId));

        PropertyInfo propertyInfo = PropertyInfoMapper.MAPPER.toDTO(entity);
        addLanguages(propertyInfo);

        return propertyInfo;
    }

    private void addLanguages(PropertyInfo propertyInfo) {

        List<PropertyInfoLanguageEntity> languageEntities = propertyInfoLanguageRepository.findAllByPropertyInfoId(propertyInfo.getId());
        List<PropertyInfoLanguage> languages = PropertyInfoLanguageMapper.MAPPER.toListDTO(languageEntities);

        propertyInfo.setLanguages(languages);
    }

    @Override
    @Transactional
    public PropertyInfo addPropertyInfo(PropertyInfoSaveRequest info) {

        Optional<PropertyInfoEntity> property = propertyInfoRepository.findByPropertyId(info.getPropertyId());
        if (property.isPresent()) {
            throw new BadRequestException("Property Info already exist for this property Id.");
        }

        PropertyInfoEntity entity = PropertyInfoMapper.MAPPER.toEntity(info);

        if (info.getBrandId() != null) {
            BrandEntity brandEntity = brandRepository.findById(info.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException(BRAND, ID, info.getBrandId()));
            entity.setBrand(brandEntity);
        }

        if (info.getPropertyTypeId() != null) {
            PropertyTypeEntity propertyTypeEntity = propertyTypeRepository.findById(info.getPropertyTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_TYPE, ID, info.getPropertyTypeId()));
            entity.setPropertyType(propertyTypeEntity);
        }

        GeoCode geoCode = info.getGeoCode();
        if (geoCode != null) {                                                              
            if (geoCode.getLatitude() == null || geoCode.getLongitude() == null) {
                throw new BadRequestException("Geo Code latitude and longitude should not be empty.");
            }

            GeoCodeEntity geoCodeEntity = GeoCodeMapper.MAPPER.toEntity(geoCode);
            GeoCodeEntity save = geoCodeRepository.save(geoCodeEntity);

            entity.setGeoCode(save);
        }

        PropertyInfoEntity propertyInfoSave = propertyInfoRepository.save(entity);

        List<PropertyInfoLanguageEntity> propertyInfoLanguages = prepareLanguagesForPropertyInfo(info, propertyInfoSave.getId());
        if (propertyInfoLanguages.size() > 0) {
            propertyInfoLanguageRepository.saveAll(propertyInfoLanguages);
        }

        PropertyInfo propertyInfo = PropertyInfoMapper.MAPPER.toDTO(propertyInfoSave);
        addLanguages(propertyInfo);

        return propertyInfo;
    }

    private List<PropertyInfoLanguageEntity> prepareLanguagesForPropertyInfo(PropertyInfoSaveRequest info, Integer infoId) {
        List<PropertyInfoLanguageEntity> result = new ArrayList<>();

        List<PropertyInfoLanguageSaveRequest> languages = info.getLanguages();

        if (languages != null) {
            for (PropertyInfoLanguageSaveRequest language : languages) {
                Integer languageId = language.getLanguageId();
                if (languageId == null) {
                    throw new BadRequestException("Language id should not be empty.");
                }

                LanguageEntity languageEntity = languageRepository.findById(languageId)
                        .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, languageId));

                PropertyInfoLanguageEntity propertyInfoLanguageEntity = new PropertyInfoLanguageEntity();
                propertyInfoLanguageEntity.setPropertyInfoId(infoId);
                propertyInfoLanguageEntity.setIsMain(language.getIsMain());
                propertyInfoLanguageEntity.setLanguage(languageEntity);

                result.add(propertyInfoLanguageEntity);
            }
        }

        return result;
    }

    @Override
    @Transactional
    public PropertyInfo updatePropertyInfo(PropertyInfoSaveRequest info) {
        if (info.getPropertyId() == null) {
            throw new BadRequestException("Property Id should not be empty.");
        }

        PropertyInfoEntity entity = propertyInfoRepository.findByPropertyId(info.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_INFO, PROPERTY_ID, info.getPropertyId()));

        GeoCode geoCode = info.getGeoCode();
        GeoCodeEntity entityGeoCode = entity.getGeoCode();
        if (geoCode == null) {
            if (entityGeoCode != null) {
                geoCodeRepository.delete(entityGeoCode);
            }
            entity.setGeoCode(null);
        } else {
            if (geoCode.getLatitude() == null || geoCode.getLongitude() == null) {
                throw new BadRequestException("Geo Code latitude and longitude should not be empty.");
            }
            if (!geoCode.getLatitude().equals(entityGeoCode.getLatitude())
                    || !geoCode.getLongitude().equals(entityGeoCode.getLongitude())) {

                entityGeoCode.setLatitude(geoCode.getLatitude());
                entityGeoCode.setLongitude(geoCode.getLongitude());

                GeoCodeEntity save = geoCodeRepository.save(entityGeoCode);
                entity.setGeoCode(save);
            }
        }

        if(info.getBrandId() == null) {
            entity.setBrand(null);
        } else {
            BrandEntity brandEntity = brandRepository.findById(info.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException(BRAND, ID, info.getBrandId()));
            entity.setBrand(brandEntity);
        }

        entity.setTaxpayerId(info.getTaxpayerId());
        entity.setWebsite(info.getWebsite());
        entity.setCapacity(info.getCapacity());
        entity.setIsExist(info.getIsExist());

        if (info.getCapacityType() == null) {
            entity.setCapacityType(null);
        } else {
            entity.setCapacityType(Enum.valueOf(CapacityType.class, info.getCapacityType()));
        }

        if(info.getPropertyTypeId() == null) {
            entity.setPropertyType(null);
        } else {
            PropertyTypeEntity propertyTypeEntity = propertyTypeRepository.findById(info.getPropertyTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_TYPE, ID, info.getPropertyTypeId()));
            entity.setPropertyType(propertyTypeEntity);
        }

        propertyInfoLanguageRepository.deleteByPropertyInfoId(entity.getId());

        List<PropertyInfoLanguageEntity> propertyInfoLanguages = prepareLanguagesForPropertyInfo(info, entity.getId());
        if (propertyInfoLanguages.size() > 0) {
            propertyInfoLanguageRepository.saveAll(propertyInfoLanguages);
        }

        PropertyInfo propertyInfo = PropertyInfoMapper.MAPPER.toDTO(entity);
        addLanguages(propertyInfo);

        log.info("Property Info with id '{}' updated to: '{}'", entity.getId(), entity.toString());

        return propertyInfo;
    }

}
