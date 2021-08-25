package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Brand;

import java.util.List;

public interface BrandManager {

    List<Brand> getBrands();

    Brand getBrandById(Integer id);

}
