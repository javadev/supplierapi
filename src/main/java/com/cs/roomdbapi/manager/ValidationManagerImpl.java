package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.model.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationManagerImpl implements ValidationManager {

    private final SellableUnitManager sellableUnitManager;

    private final PropertyManager propertyManager;

    @Override
    public void validateSellableUnitAccess(Integer sellableUnitId, HttpServletRequest req) {
        if (sellableUnitManager.sellableUnitNotExistsById(sellableUnitId)) {
            throw new BadRequestException("Sellable Unit with provided id does not exists in a system.");
        }

        Integer propertyId = sellableUnitManager.getPropertyIdBySellableUnitId(sellableUnitId);

        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validatePropertyAccess(req, supplier, propertyId);
    }

    @Override
    public void validatePropertyAccess(HttpServletRequest req, Supplier supplier, Integer propertyId) {

        String propertyName = supplier.getName();
        String supplierName = req.getRemoteUser();

        if (!isHasAllPropertiesPermission() && !supplierName.equals(propertyName)) {
            throw new BadRequestException(String.format("Property with id '%s' does not belong to supplier", propertyId));
        }
    }

    public boolean isHasAllPropertiesPermission() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals(RoleName.ROLE_SUPPLIER_ALL_PROPERTIES.getAuthority())
        );
    }

}
                                                                
