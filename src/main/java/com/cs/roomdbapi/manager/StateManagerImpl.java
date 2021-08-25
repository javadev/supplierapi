package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.State;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.StateMapper;
import com.cs.roomdbapi.model.StateEntity;
import com.cs.roomdbapi.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
public class StateManagerImpl implements StateManager {

    private final StateRepository stateRepository;

    @Override
    public List<State> getAll() {
        List<StateEntity> all = stateRepository.findAll();

        return StateMapper.MAPPER.toListDTO(all);
    }

    @Override
    public State getById(Integer id) {
        StateEntity entity = stateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STATE, ID, id));

        return StateMapper.MAPPER.toDTO(entity);
    }

    @Override
    public State getStateByCode(String code) {
        StateEntity entity = stateRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(STATE, CODE, code));

        return StateMapper.MAPPER.toDTO(entity);
    }

    @Override
    public List<State> getStatesByCountryCode(String countryCode) {
        List<StateEntity> all = stateRepository.findAllByCountry_Code(countryCode);

        return StateMapper.MAPPER.toListDTO(all);
    }

}
