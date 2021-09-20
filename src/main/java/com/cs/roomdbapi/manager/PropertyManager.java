package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;

import java.util.List;

public interface PropertyManager {

    List<Property> getProperties();

    List<Property> getPropertiesBySupplier(String supplierName);

    Property getPropertyById(Integer id);

    Supplier getSupplierByPropertyId(Integer propertyId);

    Property getPropertyBySupplierPropertyId(String id);

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

}
