package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Basket;
import com.cs.roomdbapi.dto.Description;
import com.cs.roomdbapi.dto.DescriptionSave;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.BasketMapper;
import com.cs.roomdbapi.mapper.DescriptionMapper;
import com.cs.roomdbapi.model.BasketEntity;
import com.cs.roomdbapi.model.DescriptionEntity;
import com.cs.roomdbapi.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketManagerImpl implements BasketManager {

    private final BasketRepository basketRepository;

    private final DescriptionManager descriptionManager;

    @Override
    public List<Basket> getAllBasketsByPropertyId(Integer propertyId) {
        List<BasketEntity> all = basketRepository.findAllByProperty_Id(propertyId);

        return BasketMapper.MAPPER.toListDTO(all);
    }

    @Override
    public boolean basketNotExistsById(Integer basketId) {
        return !basketRepository.existsById(basketId);
    }

    @Override
    public Basket getBasketById(Integer id) {
        BasketEntity entity = basketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BASKET, ID, id));

        return BasketMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Integer getPropertyIdByBasketId(Integer basketId) {
        return basketRepository.getPropertyIdByBasketId(basketId);
    }

    @Override
    @Transactional
    public Description addBasketDescription(Integer basketId, DescriptionSave descriptionToSave) {
        DescriptionEntity savedDescription = descriptionManager.createDescription(descriptionToSave, DEFAULT_BASKET_DESCRIPTION_TYPE_CODE);

        BasketEntity entity = basketRepository.findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException(BASKET, ID, basketId));

        entity.getDescriptions().add(savedDescription);
        basketRepository.save(entity);

        return DescriptionMapper.MAPPER.toDTO(savedDescription);
    }

    @Override
    public Integer getBasketIdByDescriptionId(Integer descriptionId) {
        return basketRepository.getBasketIdByDescriptionId(descriptionId);
    }

}
