package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Description;
import com.cs.roomdbapi.dto.DescriptionSave;
import com.cs.roomdbapi.dto.DescriptionType;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.DescriptionMapper;
import com.cs.roomdbapi.mapper.DescriptionTypeMapper;
import com.cs.roomdbapi.model.*;
import com.cs.roomdbapi.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DescriptionManagerImpl implements DescriptionManager {

    private final DescriptionTypeRepository descriptionTypeRepository;

    private final DescriptionRepository descriptionRepository;

    private final LanguageRepository languageRepository;

    private final MediaRepository mediaRepository;

    private final PointOfInterestRepository pointOfInterestRepository;

    private final PropertyRepository propertyRepository;

    @Override
    public List<DescriptionType> getAllDescriptionTypes() {
        List<DescriptionTypeEntity> all = descriptionTypeRepository.findAll();

        return DescriptionTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public DescriptionEntity createDescription(DescriptionSave descriptionToSave, String descriptionTypeCode) {
        LanguageEntity language = languageRepository.findById(descriptionToSave.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, descriptionToSave.getLanguageId()));

        if (descriptionToSave.getText().isBlank()) {
            throw new BadRequestException(String.format("Description text should not be blank for language '%s'.", language.getName()));
        }

        DescriptionTypeEntity descriptionType;
        if (descriptionToSave.getDescriptionTypeId() != null) {
            descriptionType = descriptionTypeRepository.findById(descriptionToSave.getDescriptionTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(DESCRIPTION_TYPE, ID, descriptionToSave.getDescriptionTypeId()));
        } else {
            descriptionType = descriptionTypeRepository.findByCode(descriptionTypeCode)
                    .orElseThrow(() -> new ResourceNotFoundException(DESCRIPTION_TYPE, CODE, descriptionTypeCode));
        }

        DescriptionEntity descriptionEntity = new DescriptionEntity();
        descriptionEntity.setLanguage(language);
        descriptionEntity.setText(descriptionToSave.getText());
        descriptionEntity.setDescriptionType(descriptionType);

        return descriptionRepository.save(descriptionEntity);
    }

    @Override
    public Description updateDescription(Integer descriptionId, DescriptionSave descriptionToSave) {
        DescriptionEntity descriptionEntity = descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(DESCRIPTION, ID, descriptionId));

        LanguageEntity language = languageRepository.findById(descriptionToSave.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, descriptionToSave.getLanguageId()));

        if (descriptionToSave.getDescriptionTypeId() != null) {
            DescriptionTypeEntity descriptionType = descriptionTypeRepository.findById(descriptionToSave.getDescriptionTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(DESCRIPTION_TYPE, ID, descriptionToSave.getDescriptionTypeId()));
            descriptionEntity.setDescriptionType(descriptionType);
        }

        if (descriptionToSave.getText().isBlank()) {
            throw new BadRequestException(String.format("Description text should not be blank for language '%s'.", language.getName()));
        }

        descriptionEntity.setLanguage(language);
        descriptionEntity.setText(descriptionToSave.getText());

        DescriptionEntity save = descriptionRepository.save(descriptionEntity);

        return DescriptionMapper.MAPPER.toDTO(save);
    }

    @Override
    @Transactional
    public void deleteMediaDescription(Integer mediaId, Integer id) {
        MediaEntity mediaEntity = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException(MEDIA, ID, mediaId));

        DescriptionEntity entity = descriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DESCRIPTION, ID, id));

        mediaEntity.getDescriptions().remove(entity);
        mediaRepository.save(mediaEntity);

        descriptionRepository.delete(entity);
    }

    @Override
    @Transactional
    public void deletePOIDescription(Integer poiId, Integer id) {
        PointOfInterestEntity poiEntity = pointOfInterestRepository.findById(poiId)
                .orElseThrow(() -> new ResourceNotFoundException(POINT_OF_INTEREST, ID, poiId));

        DescriptionEntity entity = descriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DESCRIPTION, ID, id));

        poiEntity.getDescriptions().remove(entity);
        pointOfInterestRepository.save(poiEntity);

        descriptionRepository.delete(entity);
    }

    @Override
    @Transactional
    public void deletePropertyDescription(Integer propertyId, Integer id) {
        PropertyEntity propertyEntity = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

        DescriptionEntity entity = descriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DESCRIPTION, ID, id));

        propertyEntity.getDescriptions().remove(entity);
        propertyRepository.save(propertyEntity);

        descriptionRepository.delete(entity);
    }

}
