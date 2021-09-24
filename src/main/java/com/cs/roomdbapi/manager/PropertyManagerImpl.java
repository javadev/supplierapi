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

    private final EmailRepository emailRepository;

    private final EmailTypeRepository emailTypeRepository;

    private final PhoneRepository phoneRepository;

    private final PhoneTypeRepository phoneTypeRepository;

    private final AddressRepository addressRepository;

    private final StateRepository stateRepository;

    private final CountryRepository countryRepository;

    private final DescriptionManager descriptionManager;

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
    public Property getOrCreatePropertyBySupplierPropertyId(String id, String supplierName) {
        Optional<PropertyEntity> optional = propertyRepository.findBySupplierPropertyId(id);

        PropertyEntity entity;
        if (optional.isPresent()) {
            entity = optional.get();
        } else {
            SupplierEntity supplier = supplierRepository.findByName(supplierName)
                    .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

            entity = new PropertyEntity();
            entity.setSupplierPropertyId(id);
            entity.setSupplier(supplier);

            PropertyEntity save = propertyRepository.save(entity);
            log.info("Property added: '{}'", save.toString());
        }

        return PropertyMapper.MAPPER.toDTO(entity);
    }

    @Override
    @Transactional
    public Property addProperty(PropertySaveRequest property, String supplierName) {
        if (propertyRepository.existsBySupplierPropertyId(property.getSupplierPropertyId())) {
            throw new BadRequestException(String.format("Property with '%s' Supplier Property Id already exists", property.getSupplierPropertyId()), property);
        }

        SupplierEntity supplier = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

        CurrencyEntity currencyEntity = getCurrencyEntity(property.getHomeCurrencyId(), property.getHomeCurrencyCode());

        PropertyEntity entity = PropertyMapper.MAPPER.saveRequestToEntity(property);
        entity.setHomeCurrency(currencyEntity);
        entity.setSupplier(supplier);

        List<EmailEntity> emailEntities = prepareEmailEntities(property.getEmails());
        entity.setEmails(emailEntities);

        List<PhoneEntity> phoneEntities = preparePhoneEntities(property.getPhones());
        entity.setPhones(phoneEntities);

        PropertyEntity save = propertyRepository.save(entity);
        log.info("Property added: '{}'", save.toString());

        return PropertyMapper.MAPPER.toDTO(save);
    }

    @Override
    @Transactional
    public Property updateProperty(Integer id, PropertySaveRequest property, String supplierName) {
        PropertyEntity entity = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, id));

        if (property.getSupplierPropertyId() != null) {
            if (!property.getSupplierPropertyId().equals(entity.getSupplierPropertyId())) {
                if (propertyRepository.existsBySupplierPropertyId(property.getSupplierPropertyId())) {
                    throw new BadRequestException(String.format("Property with '%s' Supplier Property Id already exists", property.getSupplierPropertyId()), property);
                }
            }
        }

        CurrencyEntity currencyEntity = getCurrencyEntity(property.getHomeCurrencyId(), property.getHomeCurrencyCode());

        entity.setHomeCurrency(currencyEntity);
        entity.setSupplierPropertyId(property.getSupplierPropertyId());
        entity.setCode(property.getCode());
        entity.setName(property.getName());
        entity.setAlternativeName(property.getAlternativeName());
        entity.setStatus(property.getStatus());
        entity.setForTesting(property.getForTesting());

        if (entity.getEmails() != null && !entity.getEmails().isEmpty()) {
            emailRepository.deleteAll(entity.getEmails());
        }
        List<EmailEntity> emailEntities = prepareEmailEntities(property.getEmails());
        entity.setEmails(emailEntities);

        if (entity.getPhones() != null && !entity.getPhones().isEmpty()) {
            phoneRepository.deleteAll(entity.getPhones());
        }
        List<PhoneEntity> phoneEntities = preparePhoneEntities(property.getPhones());
        entity.setPhones(phoneEntities);

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

        addAddresses(info, entity);

        PropertyInfoEntity propertyInfoSave = propertyInfoRepository.save(entity);

        List<PropertyInfoLanguageEntity> propertyInfoLanguages = prepareLanguagesForPropertyInfo(info, propertyInfoSave.getId());
        if (propertyInfoLanguages.size() > 0) {
            propertyInfoLanguageRepository.saveAll(propertyInfoLanguages);
        }

        PropertyInfo propertyInfo = PropertyInfoMapper.MAPPER.toDTO(propertyInfoSave);
        addLanguages(propertyInfo);

        return propertyInfo;
    }

    private AddressEntity prepareAddressEntity(AddressSave addressSave) {
        AddressEntity addressEntity = AddressMapper.MAPPER.toEntity(addressSave);

        LanguageEntity language = languageRepository.findById(addressSave.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, addressSave.getLanguageId()));

        StateEntity state = stateRepository.findById(addressSave.getStateId())
                .orElseThrow(() -> new ResourceNotFoundException(STATE, ID, addressSave.getStateId()));

        CountryEntity country = countryRepository.findById(addressSave.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException(COUNTRY, ID, addressSave.getCountryId()));

        addressEntity.setLanguage(language);
        addressEntity.setState(state);
        addressEntity.setCountry(country);

        return addressEntity;
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
            if (entityGeoCode == null) {
                entityGeoCode = new GeoCodeEntity();
            }
            if (!geoCode.getLatitude().equals(entityGeoCode.getLatitude())
                    || !geoCode.getLongitude().equals(entityGeoCode.getLongitude())) {

                entityGeoCode.setLatitude(geoCode.getLatitude());
                entityGeoCode.setLongitude(geoCode.getLongitude());

                GeoCodeEntity save = geoCodeRepository.save(entityGeoCode);
                entity.setGeoCode(save);
            }
        }

        if (info.getBrandId() == null) {
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

        if (info.getPropertyTypeId() == null) {
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

        if (entity.getAddresses() != null && entity.getAddresses().size() > 0) {
            addressRepository.deleteAll(entity.getAddresses());
        }
        addAddresses(info, entity);

        propertyInfoRepository.save(entity);

        PropertyInfo propertyInfo = PropertyInfoMapper.MAPPER.toDTO(entity);
        addLanguages(propertyInfo);

        log.info("Property Info with id '{}' updated to: '{}'", entity.getId(), entity.toString());

        return propertyInfo;
    }

    private void addAddresses(PropertyInfoSaveRequest info, PropertyInfoEntity entity) {
        List<AddressSave> addresses = info.getAddresses();
        if (addresses != null && addresses.size() > 0) {
            List<AddressEntity> addressEntities = new ArrayList<>();

            for (AddressSave addressSave : addresses) {
                AddressEntity addressEntity = prepareAddressEntity(addressSave);

                addressEntities.add(addressEntity);
            }

            addressRepository.saveAll(addressEntities);
            entity.setAddresses(addressEntities);
        }
    }

    @Override
    @Transactional
    public void deletePropertyInfoByPropertyId(Integer propertyId) {
        PropertyInfoEntity entity = propertyInfoRepository.findByPropertyId(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_INFO, PROPERTY_ID, propertyId));

        propertyInfoLanguageRepository.deleteByPropertyInfoId(entity.getId());

        if (entity.getGeoCode() != null) {
            geoCodeRepository.delete(entity.getGeoCode());
        }

        if (entity.getAddresses() != null && entity.getAddresses().size() > 0) {
            addressRepository.deleteAll(entity.getAddresses());
        }

        propertyInfoRepository.delete(entity);
    }

    @Override
    @Transactional
    public List<Email> setPropertyEmails(Integer propertyId, List<EmailSave> emails) {

        List<EmailEntity> emailEntities = prepareEmailEntities(emails);

        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        if (property.getEmails() != null && !property.getEmails().isEmpty()) {
            emailRepository.deleteAll(property.getEmails());
        }

        property.setEmails(emailEntities);
        propertyRepository.save(property);

        return EmailMapper.MAPPER.toListDTO(property.getEmails());
    }

    private List<EmailEntity> prepareEmailEntities(List<EmailSave> emails) {
        List<EmailEntity> emailEntities = new ArrayList<>();
        if (emails != null) {
            for (EmailSave eml : emails) {

                if (eml.getEmail() == null || eml.getEmail().isBlank()) {
                    throw new BadRequestException("Email should not be blank.");
                }

                EmailEntity entity = new EmailEntity();
                entity.setEmail(eml.getEmail());

                if (eml.getEmailTypeId() != null) {
                    EmailTypeEntity emailTypeEntity = emailTypeRepository.findById(eml.getEmailTypeId())
                            .orElseThrow(() -> new ResourceNotFoundException(EMAIL_TYPE, ID, eml.getEmailTypeId()));
                    entity.setEmailType(emailTypeEntity);
                }

                emailEntities.add(entity);
            }
        }

        if (emailEntities.size() > 0) {
            emailRepository.saveAll(emailEntities);
        }
        return emailEntities;
    }

    @Override
    @Transactional
    public List<Phone> setPropertyPhones(Integer propertyId, List<PhoneSave> phones) {

        List<PhoneEntity> phoneEntities = preparePhoneEntities(phones);

        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        if (property.getPhones() != null && !property.getPhones().isEmpty()) {
            phoneRepository.deleteAll(property.getPhones());
        }

        property.setPhones(phoneEntities);
        propertyRepository.save(property);

        return PhoneMapper.MAPPER.toListDTO(property.getPhones());
    }

    private List<PhoneEntity> preparePhoneEntities(List<PhoneSave> phones) {
        List<PhoneEntity> phoneEntities = new ArrayList<>();
        if (phones != null) {
            for (PhoneSave phone : phones) {

                if (phone.getPhoneNumber() == null || phone.getPhoneNumber().isBlank()) {
                    throw new BadRequestException("Phone should not be blank.");
                }

                PhoneEntity entity = new PhoneEntity();
                entity.setPhoneNumber(phone.getPhoneNumber());

                if (phone.getPhoneTypeId() != null) {
                    PhoneTypeEntity phoneTypeEntity = phoneTypeRepository.findById(phone.getPhoneTypeId())
                            .orElseThrow(() -> new ResourceNotFoundException(PHONE_TYPE, ID, phone.getPhoneTypeId()));
                    entity.setPhoneType(phoneTypeEntity);
                }

                if (phone.getExtension() != null) {
                    entity.setExtension(phone.getExtension());
                }

                phoneEntities.add(entity);
            }
        }
        if (phoneEntities.size() > 0) {
            phoneRepository.saveAll(phoneEntities);
        }
        return phoneEntities;
    }

    private CurrencyEntity getCurrencyEntity(Integer currencyId, String currencyCode) {
        CurrencyEntity currencyEntity = null;
        if (currencyId != null) {
            currencyEntity = currencyRepository.findById(currencyId)
                    .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, ID, currencyId));
        } else if (currencyCode != null) {
            currencyEntity = currencyRepository.findByCode(currencyCode.toUpperCase())
                    .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, CODE, currencyCode));
        }
        return currencyEntity;
    }

    @Override
    @Transactional
    public Description addPropertyDescription(Integer propertyId, DescriptionSave descriptionToSave) {
        DescriptionEntity savedDescription = descriptionManager.createDescription(descriptionToSave, null);

        PropertyEntity entity = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        entity.getDescriptions().add(savedDescription);
        propertyRepository.save(entity);

        return DescriptionMapper.MAPPER.toDTO(savedDescription);
    }

    @Override
    public Integer getPropertyIdByDescriptionId(Integer descriptionId) {
        return propertyRepository.getPropertyIdByDescriptionId(descriptionId);
    }

}
