package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.State;
import com.cs.roomdbapi.mapper.StateMapper;
import com.cs.roomdbapi.model.StateEntity;
import com.cs.roomdbapi.repository.StateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<StateEntity> findResult = stateRepository.findById(id);

        return getFromOptional(findResult);
    }

    @Override
    public State getStateByCode(String code) {
        Optional<StateEntity> findResult = stateRepository.findByCode(code);

        return getFromOptional(findResult);
    }

    @Override
    public List<State> getStatesByCountryCode(String countryCode) {
        List<StateEntity> all = stateRepository.findAllByCountry_Code(countryCode);

        return StateMapper.MAPPER.toListDTO(all);
    }

    private State getFromOptional(@NonNull Optional<StateEntity> findResult) {
        State result = null;
        if (findResult.isPresent()) {
            result = StateMapper.MAPPER.toDTO(findResult.get());
        }

        return result;
    }
}
