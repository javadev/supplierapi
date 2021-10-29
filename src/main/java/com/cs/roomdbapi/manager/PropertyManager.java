package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;

import java.util.List;

public interface PropertyManager {

    List<Property> getProperties();

    List<Property> getIsMasterProperties();

    List<Property> getPropertiesBySupplier(String supplierName);

    List<Property> getIsMasterPropertiesBySupplier(String supplierName);

    Property getPropertyById(Integer id);

    Property getPropertyIsMasterById(Integer id);

    Supplier getSupplierByPropertyId(Integer propertyId);

    Property getPropertyBySupplierPropertyId(String id);

    Property getPropertyIsMasterBySupplierPropertyId(String id);

    List<Property> getPropertiesByCode(String code);

    Property getOrCreatePropertyBySupplierPropertyId(String id, String supplierName);

    Property addProperty(PropertySaveRequest property, String supplierName);

    Property updateProperty(Integer id, PropertySaveRequest property, String supplierName);

    PropertyInfo getPropertyInfoByPropertyId(Integer propertyId);

    PropertyInfo addPropertyInfo(PropertyInfoSaveRequest info);

    PropertyInfo updatePropertyInfo(PropertyInfoSaveRequest info);

    void deletePropertyInfoByPropertyId(Integer propertyId);

    List<Email> setPropertyEmails(Integer propertyId, List<EmailSave> emails);

    List<Phone> setPropertyPhones(Integer propertyId, List<PhoneSave> phones);

    Description addPropertyDescription(Integer propertyId, DescriptionSave descriptionToSave);

    Integer getPropertyIdByDescriptionId(Integer descriptionId);

    List<PropertyIdentifier> getPropertyIdentifiersByPropertyId(Integer propertyId);

    List<PropertyIdentifier> addPropertyIdentifiers(Integer propertyId, List<PropertyIdentifierSave> propertyIdentifierSave);

    void deletePropertyIdentifier(Integer propertyId, Integer sourceId);

}
