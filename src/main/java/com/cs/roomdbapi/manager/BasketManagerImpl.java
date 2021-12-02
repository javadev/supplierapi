package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.BasketMapper;
import com.cs.roomdbapi.mapper.BasketSellableUnitMapper;
import com.cs.roomdbapi.mapper.DescriptionMapper;
import com.cs.roomdbapi.model.*;
import com.cs.roomdbapi.repository.BasketRepository;
import com.cs.roomdbapi.repository.BasketSellableUnitRepository;
import com.cs.roomdbapi.repository.PropertyRepository;
import com.cs.roomdbapi.repository.SellableUnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketManagerImpl implements BasketManager {

    private final BasketRepository basketRepository;

    private final DescriptionManager descriptionManager;

    private final BasketSellableUnitRepository basketSellableUnitRepository;

    private final SellableUnitRepository sellableUnitRepository;

    private final PropertyRepository propertyRepository;

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
    public Basket addBasket(Basket basket) {
        if (basketRepository.existsByProperty_IdAndName(basket.getPropertyId(), basket.getName())) {
            throw new BadRequestException(String.format("Basket with name '%s' already exists for property with id '%s'",
                    basket.getName(), basket.getPropertyId()));
        }

        PropertyEntity propertyEntity = propertyRepository.findById(basket.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, basket.getPropertyId()));

        BasketEntity entity = BasketMapper.MAPPER.saveRequestToEntity(basket);
        entity.setProperty(propertyEntity);

        BasketEntity save = basketRepository.save(entity);
        log.info("REQUEST_ID: {}. Basket added: '{}'", MDC.get("REQUEST_ID"), save.toString());

        return BasketMapper.MAPPER.toDTO(save);
    }

    @Override
    public Basket updateBasket(Integer id, BasketUpdate basket) {
        BasketEntity entity = basketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BASKET, ID, id));

        if (!basket.getName().equals(entity.getName())) {
            if (basketRepository.existsByProperty_IdAndName(entity.getProperty().getId(), basket.getName())) {
                throw new BadRequestException(String.format("Basket with name '%s' already exists for property with id '%s'",
                        basket.getName(), entity.getProperty().getId()));
            }
            entity.setName(basket.getName());
        }

        entity.setIsVisible(basket.getIsVisible());

        BasketEntity save = basketRepository.save(entity);
        log.info("REQUEST_ID: {}. Basket with id '{}' updated to: '{}'", MDC.get("REQUEST_ID"), entity.getId(), entity.toString());

        return BasketMapper.MAPPER.toDTO(save);
    }

    @Override
    @Transactional
    public void deleteBasket(Integer id) {
        BasketEntity entity = basketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BASKET, ID, id));

        // TODO check that basket is not attached to Product. If it is - response with error message.

        List<BasketSellableUnitEntity> all = basketSellableUnitRepository.findAllByBasketId(id);
        if (all.size() > 0) {
            basketSellableUnitRepository.deleteAll(all);
        }

        basketRepository.delete(entity);
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

    @Override
    public List<BasketSellableUnit> getSellableUnitsByBasketId(Integer basketId) {
        List<BasketSellableUnitEntity> all = basketSellableUnitRepository.findAllByBasketId(basketId);

        return BasketSellableUnitMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public List<BasketSellableUnit> setSellableUnits(BasketSellableUnitRequest basketSellableUnits) {
        Integer basketId = basketSellableUnits.getBasketId();

        List<BasketSellableUnitEntity> bsu = new ArrayList<>();
        for (BasketSellableUnitSave unit : basketSellableUnits.getSellableUnits()) {

            SellableUnitEntity su = sellableUnitRepository.findById(unit.getSellableUnitId())
                    .orElseThrow(() -> new ResourceNotFoundException(SELLABLE_UNIT, ID, unit.getSellableUnitId()));

            BasketSellableUnitEntity entity = new BasketSellableUnitEntity();
            entity.setBasketId(basketId);
            entity.setSellableUnit(su);
            entity.setQuantity(unit.getQuantity());
            entity.setConsecutiveOverTime(unit.getConsecutiveOverTime());

            bsu.add(entity);
        }

        basketSellableUnitRepository.deleteByBasketId(basketId);
        if (bsu.size() > 0) {
            bsu = basketSellableUnitRepository.saveAll(bsu);
        }

        return BasketSellableUnitMapper.MAPPER.toListDTO(bsu);
    }

    @Override
    public BasketSellableUnit addSellableUnit(BasketSellableUnitSaveOne req) {
        Integer basketId = req.getBasketId();

        Integer suId = req.getSellableUnitId();
        SellableUnitEntity su = sellableUnitRepository.findById(suId)
                .orElseThrow(() -> new ResourceNotFoundException(SELLABLE_UNIT, ID, suId));

        Optional<BasketSellableUnitEntity> existing = basketSellableUnitRepository.findTopByBasketIdAndSellableUnit_Id(basketId, suId);

        BasketSellableUnitEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
        } else {
            entity = new BasketSellableUnitEntity();
            entity.setBasketId(basketId);
            entity.setSellableUnit(su);
        }
        entity.setQuantity(req.getQuantity());
        entity.setConsecutiveOverTime(req.getConsecutiveOverTime());

        BasketSellableUnitEntity save = basketSellableUnitRepository.save(entity);

        return BasketSellableUnitMapper.MAPPER.toDTO(save);
    }

    @Override
    public List<Basket> getBasketsBySellableUnitId(Integer sellableUnitId) {
        List<BasketSellableUnitEntity> basketSellableUnits = basketSellableUnitRepository.findAllBySellableUnit_Id(sellableUnitId);

        List<BasketEntity> all = new ArrayList<>();
        if (basketSellableUnits.size() > 0) {
            List<Integer> ids = basketSellableUnits.stream()
                    .map(BasketSellableUnitEntity::getBasketId)
                    .collect(Collectors.toList());

            all = basketRepository.findAllById(ids);
        }

        return BasketMapper.MAPPER.toListDTO(all);
    }

    @Override
    public void deleteSellableUnitFromBasket(Integer basketId, Integer sellableUnitId) {
        BasketSellableUnitEntity entity = basketSellableUnitRepository.findTopByBasketIdAndSellableUnit_Id(basketId, sellableUnitId)
                .orElseThrow(() -> new BadRequestException(String.format("Sellable Unit with id '%s' is not included in basket with id '%s'",
                        sellableUnitId, basketId)));

        basketSellableUnitRepository.delete(entity);
    }

}
