package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Currency;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.CurrencyMapper;
import com.cs.roomdbapi.model.CurrencyEntity;
import com.cs.roomdbapi.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyManagerImpl implements CurrencyManager {

    private final CurrencyRepository currencyRepository;

    private final NotificationManager notificationManager;

    @Override
    public List<Currency> getCurrencies() {
        List<CurrencyEntity> all = currencyRepository.findAll();

        return CurrencyMapper.MAPPER.toListDTO(all);
    }

    @Override
    public Currency getCurrencyById(Integer id) {
        CurrencyEntity entity = currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, ID, id));

        return CurrencyMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Currency getCurrencyByLettersCode(String code) {
        CurrencyEntity entity = currencyRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, CODE, code));

        return CurrencyMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Currency getCurrencyByNumericCode(Integer code) {
        CurrencyEntity entity = currencyRepository.findByNumericCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, CODE, code));

        return CurrencyMapper.MAPPER.toDTO(entity);
    }

    @Override
    public Currency addCurrency(Currency currency) {
        if (currencyRepository.existsByCode(currency.getCode())) {
            throw new BadRequestException(String.format("Currency with '%s' code already exists", currency.getCode()), currency);
        }

        if (currencyRepository.existsByNumericCode(currency.getNumericCode())) {
            throw new BadRequestException(String.format("Currency with '%s' numeric code already exists", currency.getNumericCode()), currency);
        }

        CurrencyEntity save = currencyRepository.save(CurrencyMapper.MAPPER.toEntity(currency));   // Check that id is not saved (mapped)
        log.info("Currency added: '{}'", save.toString());

        notificationManager.addBatchNotifications(CURRENCY, save.getId());

        return CurrencyMapper.MAPPER.toDTO(save);
    }

    @Override
    public Currency updateCurrency(Integer id, Currency currency) {
        CurrencyEntity entity = currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CURRENCY, ID, id));

        if (!entity.getCode().equals(currency.getCode())) {
            if (currencyRepository.existsByCode(currency.getCode())) {
                throw new BadRequestException(String.format("Currency with '%s' code already exists", currency.getCode()), currency);
            }
            entity.setCode(currency.getCode());
        }

        if (!entity.getNumericCode().equals(currency.getNumericCode())) {
            if (currencyRepository.existsByNumericCode(currency.getNumericCode())) {
                throw new BadRequestException(String.format("Currency with '%s' numeric code already exists", currency.getNumericCode()), currency);
            }
            entity.setNumericCode(currency.getNumericCode());
        }

        entity.setName(currency.getName());
        entity.setMinorUnit(currency.getMinorUnit());

        CurrencyEntity save = currencyRepository.save(entity);
        log.info("Currency with id '{}' updated to: '{}'", entity.getId(), entity.toString());

        notificationManager.addBatchNotifications(CURRENCY, save.getId());

        return CurrencyMapper.MAPPER.toDTO(save);
    }

}
