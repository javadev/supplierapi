package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Brand;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.BrandMapper;
import com.cs.roomdbapi.model.BrandEntity;
import com.cs.roomdbapi.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.BRAND;
import static com.cs.roomdbapi.utilities.AppUtils.ID;

@Service
@RequiredArgsConstructor
public class BrandManagerImpl implements BrandManager {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getBrands() {
        List<BrandEntity> all = brandRepository.findAll();

        return BrandMapper.MAPPER.toListDTO(all);
    }

    @Override
    public Brand getBrandById(Integer id) {
        BrandEntity entity = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BRAND, ID, id));

        return BrandMapper.MAPPER.toDTO(entity);
    }

}
