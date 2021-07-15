package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Currency;

import java.util.List;

public interface CurrencyManager {

    List<Currency> getCurrencies();

    Currency getCurrencyById(Integer id);

    Currency getCurrencyByLettersCode(String code);

    Currency getCurrencyByNumericCode(Integer code);

    Currency addCurrency(Currency currency);

    Currency updateCurrency(Integer id, Currency currency);

}
