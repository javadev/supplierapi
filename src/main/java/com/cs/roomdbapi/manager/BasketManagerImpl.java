package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Basket;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.BasketMapper;
import com.cs.roomdbapi.model.BasketEntity;
import com.cs.roomdbapi.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.BASKET;
import static com.cs.roomdbapi.utilities.AppUtils.ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketManagerImpl implements BasketManager {

    private final BasketRepository basketRepository;

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

}
