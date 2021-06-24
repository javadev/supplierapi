package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.State;

import java.util.List;

public interface StateManager {

    List<State> getAll();

    State getById(Integer id);

    State getStateByCode(String code);

    List<State> getStatesByCountryCode(String countryCode);

}
