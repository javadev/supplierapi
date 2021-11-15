package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.PricingModelType;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.PricingModelTypeMapper;
import com.cs.roomdbapi.model.PricingModelTypeEntity;
import com.cs.roomdbapi.repository.PricingModelTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.ID;
import static com.cs.roomdbapi.utilities.AppUtils.PRICING_MODEL_TYPE;

@Service
@RequiredArgsConstructor
public class PricingModelManagerImpl implements PricingModelManager {

    private final PricingModelTypeRepository pricingModelTypeRepository;

    @Override
    public List<PricingModelType> getPricingModelTypes() {
        List<PricingModelTypeEntity> all = pricingModelTypeRepository.findAll();

        return PricingModelTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public PricingModelType getPricingModelTypeById(Integer id) {
        PricingModelTypeEntity entity = pricingModelTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL_TYPE, ID, id));

        return PricingModelTypeMapper.MAPPER.toDTO(entity);
    }

}
