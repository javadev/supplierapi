package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;

import java.util.List;

public interface PropertyManager {

    List<Property> getProperties();

    List<Property> getPropertiesByCultSwitchIds(List<String> csIds);

    List<Property> getIsMasterProperties();

    List<Property> getPropertiesBySupplier(String supplierName);

    List<Property> getIsMasterPropertiesBySupplier(String supplierName);

    Property getPropertyById(Integer id);

    Property getPropertyIsMasterById(Integer id);

    Supplier getSupplierByPropertyId(Integer propertyId);

    Property getPropertyByCultSwitchId(String id);

    Property getPropertyIsMasterByCultSwitchId(String id);

    List<Property> getPropertiesByCode(String code);

    Property getOrCreatePropertyByCultSwitchId(String csId, String supplierName);

    Property addProperty(PropertySaveRequest property, String supplierName);

    Property updateProperty(Integer id, PropertySaveRequest property, String supplierName);

    PropertyInfo getPropertyInfoByPropertyId(Integer propertyId);

    PropertyInfo addPropertyInfo(PropertyInfoSaveRequest info);

    PropertyInfo updatePropertyInfo(PropertyInfoSaveRequest info);

    void deletePropertyInfoByPropertyId(Integer propertyId);

    List<Email> setPropertyEmails(Integer propertyId, List<EmailSave> emails);

    List<Phone> setPropertyPhones(Integer propertyId, List<PhoneSave> phones);

    Currency setPropertyHomeCurrencyById(Integer propertyId, Integer currencyId);

    Currency setPropertyHomeCurrencyByCode(Integer propertyId, String currencyCode);

    Description addPropertyDescription(Integer propertyId, DescriptionSave descriptionToSave);

    Integer getPropertyIdByDescriptionId(Integer descriptionId);

    List<PropertyIdentifier> getPropertyIdentifiersByPropertyId(Integer propertyId);

    List<PropertyIdentifier> addPropertyIdentifiers(Integer propertyId, List<PropertyIdentifierSave> propertyIdentifierSave);

    void deletePropertyIdentifier(Integer propertyId, Integer sourceId);

}
