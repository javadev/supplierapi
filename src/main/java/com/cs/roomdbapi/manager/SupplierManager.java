package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.dto.SupplierWebhook;

import java.util.List;

public interface SupplierManager {

    String signIn(String supplierName, String password);

    String refreshToken(String supplierName);

    SupplierWebhook saveWebhook(String supplierName, SupplierWebhook webhookUpdate);

    Supplier getByName(String supplierName);

    List<Supplier> getSuppliers();

    List<Supplier> getSuppliersActiveWithWebhook();

    String getWebhookById(Integer supplierId);

}
