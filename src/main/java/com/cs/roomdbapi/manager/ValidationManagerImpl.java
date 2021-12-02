package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Property;
import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.model.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationManagerImpl implements ValidationManager {

    private final SellableUnitManager sellableUnitManager;

    private final PricingModelManager pricingModelManager;

    private final RatePlanManager ratePlanManager;

    private final ProductManager productManager;

    private final PropertyManager propertyManager;

    @Override
    public void validateSellableUnitAccess(Integer sellableUnitId, HttpServletRequest req) {
        if (sellableUnitManager.sellableUnitNotExistsById(sellableUnitId)) {
            throw new BadRequestException("Sellable Unit with provided id does not exists in a system.");
        }

        Integer propertyId = sellableUnitManager.getPropertyIdBySellableUnitId(sellableUnitId);
        validatePropertyAccess(req, propertyId);
    }

    @Override
    public void validatePricingModelAccess(Integer pricingModelId, HttpServletRequest req) {
        if (pricingModelManager.pricingModelNotExistsById(pricingModelId)) {
            throw new BadRequestException("Pricing Model with provided id does not exists in a system.");
        }

        Integer propertyId = pricingModelManager.getPropertyIdByPricingModelId(pricingModelId);
        validatePropertyAccess(req, propertyId);
    }

    @Override
    public void validateRatePlanAccess(Integer ratePlanId, HttpServletRequest req) {
        if (ratePlanManager.ratePlanNotExistsById(ratePlanId)) {
            throw new BadRequestException("Rate Plan with provided id does not exists in a system.");
        }

        Integer propertyId = ratePlanManager.getPropertyIdByRatePlanId(ratePlanId);
        validatePropertyAccess(req, propertyId);
    }

    @Override
    public void validateProductAccess(Integer productId, HttpServletRequest req) {
        if (productManager.productNotExistsById(productId)) {
            throw new BadRequestException("Product with provided id does not exists in a system.");
        }

        Integer propertyId = productManager.getPropertyIdByProductId(productId);
        validatePropertyAccess(req, propertyId);
    }

    @Override
    public void validatePropertyAccess(HttpServletRequest req, Supplier supplier, Integer propertyId) {

        String propertySupplierName = supplier.getName();
        String supplierName = req.getRemoteUser();

        if (!isHasAllPropertiesPermission() && !supplierName.equals(propertySupplierName)) {
            throw new BadRequestException(String.format("Property with id '%s' does not belong to supplier", propertyId));
        }
    }

    @Override
    public void validatePropertyAccess(HttpServletRequest req, Integer propertyId) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);

        String propertySupplierName = supplier.getName();
        String supplierName = req.getRemoteUser();

        if (!isHasAllPropertiesPermission() && !supplierName.equals(propertySupplierName)) {
            throw new BadRequestException(String.format("Property with id '%s' does not belong to supplier", propertyId));
        }
    }

    @Override
    public void validatePropertiesList(HttpServletRequest req, List<Property> properties) {
        if (isHasAllPropertiesPermission() && properties == null) {
            return;
        }

        for (Property property : properties) {
            String propertySupplierName = property.getSupplier().getName();
            String supplierName = req.getRemoteUser();

            if (!supplierName.equals(propertySupplierName)) {
                throw new BadRequestException(String.format("Property with id '%s' does not belong to supplier", property.getId()));
            }
        }
    }

    @Override
    public boolean hasAccessToProperty(HttpServletRequest req, Supplier supplier, Integer propertyId) {

        String propertySupplierName = supplier.getName();
        String supplierName = req.getRemoteUser();

        return isHasAllPropertiesPermission() || supplierName.equals(propertySupplierName);
    }

    public boolean isHasAllPropertiesPermission() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals(RoleName.ROLE_SUPPLIER_ALL_PROPERTIES.getAuthority())
        );
    }

}
                                                                
