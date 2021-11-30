package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Product;

import java.util.List;

public interface ProductManager {

    List<Product> getProducts();

    List<Product> getAllProductsForProperty(Integer propertyId);

    Product getProductById(Integer id);

    boolean productNotExistsById(Integer productId);

    Integer getPropertyIdByProductId(Integer productId);

}
