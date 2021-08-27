package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.PhoneType;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.PhoneTypeMapper;
import com.cs.roomdbapi.model.PhoneTypeEntity;
import com.cs.roomdbapi.repository.PhoneTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.ID;
import static com.cs.roomdbapi.utilities.AppUtils.PHONE_TYPE;

@Service
@RequiredArgsConstructor
public class PhoneTypeManagerImpl implements PhoneTypeManager {

    private final PhoneTypeRepository phoneTypeRepository;

    @Override
    public List<PhoneType> getPhoneType() {
        List<PhoneTypeEntity> all = phoneTypeRepository.findAll();

        return PhoneTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public PhoneType getPhoneTypeById(Integer id) {
        PhoneTypeEntity entity = phoneTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PHONE_TYPE, ID, id));

        return PhoneTypeMapper.MAPPER.toDTO(entity);
    }

}
