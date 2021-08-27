package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.EmailType;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.EmailTypeMapper;
import com.cs.roomdbapi.model.EmailTypeEntity;
import com.cs.roomdbapi.repository.EmailTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.EMAIL_TYPE;
import static com.cs.roomdbapi.utilities.AppUtils.ID;

@Service
@RequiredArgsConstructor
public class EmailTypeManagerImpl implements EmailTypeManager {

    private final EmailTypeRepository emailTypeRepository;

    @Override
    public List<EmailType> getEmailTypes() {
        List<EmailTypeEntity> all = emailTypeRepository.findAll();

        return EmailTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public EmailType getEmailTypeById(Integer id) {
        EmailTypeEntity entity = emailTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMAIL_TYPE, ID, id));

        return EmailTypeMapper.MAPPER.toDTO(entity);
    }

}
