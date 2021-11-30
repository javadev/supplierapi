package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Product;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.ProductMapper;
import com.cs.roomdbapi.model.ProductEntity;
import com.cs.roomdbapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.ID;
import static com.cs.roomdbapi.utilities.AppUtils.PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductManagerImpl implements ProductManager {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        List<ProductEntity> all = productRepository.findAll();

        return ProductMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<Product> getAllProductsForProperty(Integer propertyId) {
        List<ProductEntity> all = productRepository.findAllByProperty_Id(propertyId);

        return ProductMapper.MAPPER.toListDTO(all);
    }

    @Override
    public Product getProductById(Integer id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, id));

        return ProductMapper.MAPPER.toDTO(entity);
    }

    @Override
    public boolean productNotExistsById(Integer productId) {
        return !productRepository.existsById(productId);
    }

    @Override
    public Integer getPropertyIdByProductId(Integer productId) {
        return productRepository.getPropertyIdByProductId(productId);
    }

}
