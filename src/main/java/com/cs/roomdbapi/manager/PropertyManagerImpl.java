package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.*;
import com.cs.roomdbapi.model.*;
import com.cs.roomdbapi.repository.*;
import com.cs.roomdbapi.utilities.Commons;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final PropertyIdentifierRepository propertyIdentifierRepository;

    private final IdentifierSourceRepository identifierSourceRepository;

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
    public List<Property> toListDTO(List<PropertyEntity> list) {
        return PropertyMapper.MAPPER.toListDTO(list);
    }

    @Override
    public List<Property> getProperties() {
        List<PropertyEntity> all = propertyRepository.findAll();

        return PropertyMapper.MAPPER.toListDTO(all);
    }

    @Override
    public Page<PropertyEntity> getPropertiesPagination(Integer pageNumber, Integer pageSize, String sortBy, boolean sortDesc) {
        Sort sort = Commons.getOrder(sortBy, sortDesc);
        pageSize = Commons.getPageSize(pageSize);

        PageRequest paging = PageRequest.of(pageNumber, pageSize, sort);

        return propertyRepository.findAll(paging);
    }

    @Override
    public List<Property> getPropertiesByCultSwitchIds(List<String> csIds) {
        List<PropertyEntity> all = propertyRepository.findAllByCultSwitchIdIn(csIds);

        return PropertyMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<Property> getIsMasterProperties() {
        List<PropertyEntity> all = propertyRepository.findAllByIsMaster(true);

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
    public Page<PropertyEntity> getPropertiesBySupplierPagination(String supplierName, Integer pageNumber, Integer pageSize, String sortBy, boolean sortDesc) {
        SupplierEntity supplier = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

        Sort sort = Commons.getOrder(sortBy, sortDesc);
        pageSize = Commons.getPageSize(pageSize);

        PageRequest paging = PageRequest.of(pageNumber, pageSize, sort);

        return propertyRepository.findAllBySupplierIs(supplier, paging);
    }

    @Override
    public List<Property> getIsMasterPropertiesBySupplier(String supplierName) {
        SupplierEntity supplier = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

        List<PropertyEntity> all = propertyRepository.findAllBySupplierIsAndIsMaster(supplier, true);

        return PropertyMapper.MAPPER.toListDTOWithoutSupplier(all);
    }

    @Override
    public Property getPropertyById(Integer id) {
        PropertyEntity entity = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, id));

        return PropertyMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Property getPropertyIsMasterById(Integer id) {
        PropertyEntity entity = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, id));

        return PropertyMapper.MAPPER.toDTOIsMaster(entity);
    }

    @Override
    public Supplier getSupplierByPropertyId(Integer propertyId) {
        PropertyEntity entity = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        return SupplierMapper.MAPPER.toDTO(entity.getSupplier());
    }

    @Override
    public Property getPropertyByCultSwitchId(String id) {
        PropertyEntity entity = propertyRepository.findByCultSwitchId(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, CULTSWITCH_ID, id));

        return PropertyMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Property getPropertyByExternalSystemId(String id, String sourceAbbr) {

        PropertyEntity entity;

        if (CULT_SWITCH_IDENTIFIER_SOURCE_ABBR.equals(sourceAbbr)) {
            entity = propertyRepository.findByCultSwitchId(id)
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, CULTSWITCH_ID, id));
        } else if (ROOM_DB_IDENTIFIER_SOURCE_ABBR.equals(sourceAbbr)) {
            entity = propertyRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, id));
        } else {
            IdentifierSourceEntity sourceEntity = identifierSourceRepository.findByAbbreviation(sourceAbbr)
                    .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ABBREVIATION, sourceAbbr));

            PropertyIdentifierEntity identifierEntity = propertyIdentifierRepository.findByIdentifierAndSource(id, sourceEntity)
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_IDENTIFIER, SOURCE_ABBREVIATION, sourceAbbr));

            entity = propertyRepository.findById(identifierEntity.getPropertyId())
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, identifierEntity.getPropertyId()));
        }
        
        return PropertyMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Property getPropertyIsMasterByCultSwitchId(String id) {
        PropertyEntity entity = propertyRepository.findByCultSwitchId(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, CULTSWITCH_ID, id));

        return PropertyMapper.MAPPER.toDTOIsMaster(entity);
    }

    @Override
    public List<Property> getPropertiesByCode(String code) {
        List<PropertyEntity> all = propertyRepository.findAllByCode(code);

        return PropertyMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public Property getOrCreatePropertyByCultSwitchId(String csId, String supplierName) {
        Optional<PropertyEntity> optional = propertyRepository.findByCultSwitchId(csId);

        PropertyEntity entity;
        if (optional.isPresent()) {
            entity = optional.get();
        } else {
            SupplierEntity supplier = supplierRepository.findByName(supplierName)
                    .orElseThrow(() -> new ResourceNotFoundException(SUPPLIER, NAME, supplierName));

            entity = new PropertyEntity();
            entity.setCultSwitchId(csId);
            entity.setSupplier(supplier);

            PropertyEntity save = propertyRepository.save(entity);
            log.info("Property added: '{}'", save.toString());
        }

        return PropertyMapper.MAPPER.toDTO(entity);
    }

    @Override
    @Transactional
    public Property addProperty(PropertySaveRequest property, String supplierName) {
        if (property.getCultSwitchId() != null) {
            if (propertyRepository.existsByCultSwitchId(property.getCultSwitchId())) {
                throw new BadRequestException(String.format("Property with '%s' CultSwitch Id already exists", property.getCultSwitchId()), property);
            }
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

        if (property.getCultSwitchId() != null) {
            if (!property.getCultSwitchId().equals(entity.getCultSwitchId())) {
                if (propertyRepository.existsByCultSwitchId(property.getCultSwitchId())) {
                    throw new BadRequestException(String.format("Property with '%s' CultSwitch Id already exists", property.getCultSwitchId()), property);
                }
            }
        }

        CurrencyEntity currencyEntity = getCurrencyEntity(property.getHomeCurrencyId(), property.getHomeCurrencyCode());

        entity.setHomeCurrency(currencyEntity);
        entity.setCultSwitchId(property.getCultSwitchId());
        entity.setCode(property.getCode());
        entity.setName(property.getName());
        entity.setAlternativeName(property.getAlternativeName());
        entity.setStatus(property.getStatus());
        entity.setForTesting(property.getForTesting());
        entity.setIsMaster(property.getIsMaster());

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

        if (addressSave.getLanguageId() != null) {
            LanguageEntity language = languageRepository.findById(addressSave.getLanguageId())
                    .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, addressSave.getLanguageId()));
            addressEntity.setLanguage(language);
        }

        if (addressSave.getStateId() != null) {
            StateEntity state = stateRepository.findById(addressSave.getStateId())
                    .orElseThrow(() -> new ResourceNotFoundException(STATE, ID, addressSave.getStateId()));
            addressEntity.setState(state);
        }

        if (addressSave.getCountryId() != null) {
            CountryEntity country = countryRepository.findById(addressSave.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException(COUNTRY, ID, addressSave.getCountryId()));
            addressEntity.setCountry(country);
        }

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

    @Override
    @Transactional
    public Currency setPropertyHomeCurrencyById(Integer propertyId, Integer currencyId) {
        PropertyEntity entity = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        CurrencyEntity currencyEntity = currencyRepository.findById(currencyId)
                    .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, ID, currencyId));

        entity.setHomeCurrency(currencyEntity);

        return CurrencyMapper.MAPPER.toDTO(currencyEntity);
    }

    @Override
    @Transactional
    public Currency setPropertyHomeCurrencyByCode(Integer propertyId, String currencyCode) {
        PropertyEntity entity = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        CurrencyEntity currencyEntity = currencyRepository.findByCode(currencyCode)
                    .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, CODE, currencyCode));

        entity.setHomeCurrency(currencyEntity);

        return CurrencyMapper.MAPPER.toDTO(currencyEntity);
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
                } else {
                    entity.setPhoneType(null);
                }

                entity.setExtension(phone.getExtension());
                entity.setIsValidated(phone.getIsValidated());

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
        DescriptionEntity savedDescription = descriptionManager.createDescription(descriptionToSave, DEFAULT_PROPERTY_DESCRIPTION_TYPE_CODE);

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

    @Override
    public List<PropertyIdentifier> getPropertyIdentifiersByPropertyId(Integer propertyId) {
        List<PropertyIdentifierEntity> all = propertyIdentifierRepository.findAllByPropertyId(propertyId);

        PropertyEntity propertyEntity = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        IdentifierSourceEntity rdbSourceEntity = identifierSourceRepository.findByAbbreviation(ROOM_DB_IDENTIFIER_SOURCE_ABBR)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ABBREVIATION, ROOM_DB_IDENTIFIER_SOURCE_ABBR));

        IdentifierSourceEntity csSourceEntity = identifierSourceRepository.findByAbbreviation(CULT_SWITCH_IDENTIFIER_SOURCE_ABBR)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ABBREVIATION, CULT_SWITCH_IDENTIFIER_SOURCE_ABBR));

        // Add RoomDB In in result
        PropertyIdentifierEntity roomDBId = new PropertyIdentifierEntity();
        roomDBId.setIdentifier(propertyEntity.getId().toString());
        roomDBId.setPropertyId(propertyEntity.getId());
        roomDBId.setSource(rdbSourceEntity);

        all.add(roomDBId);

        if (propertyEntity.getCultSwitchId() != null) {
            // Add CultSwitch Id in result
            PropertyIdentifierEntity cultSwitchId = new PropertyIdentifierEntity();
            cultSwitchId.setIdentifier(propertyEntity.getCultSwitchId());
            cultSwitchId.setPropertyId(propertyEntity.getId());
            cultSwitchId.setSource(csSourceEntity);

            all.add(cultSwitchId);
        }

        return PropertyIdentifierMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public List<PropertyIdentifier> addPropertyIdentifiers(Integer propertyId, List<PropertyIdentifierSave> identifiers) {
        List<PropertyIdentifierEntity> entities = new ArrayList<>();

        IdentifierSourceEntity rdbSourceEntity = identifierSourceRepository.findByAbbreviation(ROOM_DB_IDENTIFIER_SOURCE_ABBR)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ABBREVIATION, ROOM_DB_IDENTIFIER_SOURCE_ABBR));

        IdentifierSourceEntity csSourceEntity = identifierSourceRepository.findByAbbreviation(CULT_SWITCH_IDENTIFIER_SOURCE_ABBR)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ABBREVIATION, CULT_SWITCH_IDENTIFIER_SOURCE_ABBR));

        PropertyIdentifierEntity cultSwitchId = null;

        for (PropertyIdentifierSave identifier : identifiers) {
            IdentifierSourceEntity sourceEntity = identifierSourceRepository.findById(identifier.getSourceId())
                    .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ID, identifier.getSourceId()));

            if (identifier.getIdentifier() == null || identifier.getIdentifier().isBlank()) {
                throw new BadRequestException("Identifier should not be blank.");
            }
            if (sourceEntity.getId().equals(rdbSourceEntity.getId())) {
                throw new BadRequestException("It's not possible to update RoomDB identifier.");
            }

            if (sourceEntity.getId().equals(csSourceEntity.getId())) {

                // Update CultSwitch id
                PropertyEntity propertyEntity = propertyRepository.findById(propertyId)
                        .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

                propertyEntity.setCultSwitchId(identifier.getIdentifier());
                propertyRepository.save(propertyEntity);

                cultSwitchId = new PropertyIdentifierEntity();
                cultSwitchId.setIdentifier(propertyEntity.getCultSwitchId());
                cultSwitchId.setPropertyId(propertyEntity.getId());
                cultSwitchId.setSource(csSourceEntity);

            } else {

                // Update other ids
                PropertyIdentifierEntity entity = propertyIdentifierRepository.findByPropertyIdAndSource(propertyId, sourceEntity)
                        .orElseGet(PropertyIdentifierEntity::new);

                if (entity.getPropertyId() == null) {
                    entity.setPropertyId(propertyId);
                    entity.setSource(sourceEntity);

                    log.info("REQUEST_ID: {}. New property identifier created for source: '{}'", MDC.get("REQUEST_ID"), sourceEntity.getAbbreviation());
                } else {
                    log.info("REQUEST_ID: {}. Existing property identifier will be updated for source: '{}'", MDC.get("REQUEST_ID"), sourceEntity.getAbbreviation());
                }

                entity.setIdentifier(identifier.getIdentifier());
                entities.add(entity);
            }
        }

        List<PropertyIdentifierEntity> saveAll = new ArrayList<>();
        if (entities.size() > 0) {
            saveAll = propertyIdentifierRepository.saveAll(entities);
        }

        if (cultSwitchId != null) {
            saveAll.add(cultSwitchId);
        }

        return PropertyIdentifierMapper.MAPPER.toListDTO(saveAll);
    }

    @Override
    @Transactional
    public void deletePropertyIdentifier(Integer propertyId, Integer sourceId) {
        IdentifierSourceEntity sourceEntity = identifierSourceRepository.findById(sourceId)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ID, sourceId));

        // Check case when source is RoomDB -- it's not possible to remove this id
        IdentifierSourceEntity rdbSourceEntity = identifierSourceRepository.findByAbbreviation(ROOM_DB_IDENTIFIER_SOURCE_ABBR)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ABBREVIATION, ROOM_DB_IDENTIFIER_SOURCE_ABBR));

        if (sourceEntity.getId().equals(rdbSourceEntity.getId())) {
            throw new BadRequestException("It's not possible to delete RoomDB identifier.");
        }

        // Check case when source is CultSwitch -- this id is stored in different plase than other ids
        IdentifierSourceEntity csSourceEntity = identifierSourceRepository.findByAbbreviation(CULT_SWITCH_IDENTIFIER_SOURCE_ABBR)
                .orElseThrow(() -> new ResourceNotFoundException(IDENTIFIER_SOURCE, ABBREVIATION, CULT_SWITCH_IDENTIFIER_SOURCE_ABBR));

        if (sourceEntity.getId().equals(csSourceEntity.getId())) {
            PropertyEntity propertyEntity = propertyRepository.findById(propertyId)
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

            propertyEntity.setCultSwitchId(null);
            propertyRepository.save(propertyEntity);
        } else {
            PropertyIdentifierEntity entity = propertyIdentifierRepository.findByPropertyIdAndSource(propertyId, sourceEntity)
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY_IDENTIFIER, SOURCE_ID, sourceId));

            propertyIdentifierRepository.delete(entity);
        }
    }

}
