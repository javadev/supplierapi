package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Product;

import java.util.List;

public interface ProductManager {

    List<Product> getAllProductsByPropertyId(Integer propertyId);

    boolean productNotExistsById(Integer productId);

    Product getProductById(Integer id);

    Integer getPropertyIdByProductId(Integer productId);

}
