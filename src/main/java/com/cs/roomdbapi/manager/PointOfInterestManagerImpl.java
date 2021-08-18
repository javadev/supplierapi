package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.CategoryCodeMapper;
import com.cs.roomdbapi.mapper.DescriptionMapper;
import com.cs.roomdbapi.mapper.PointOfInterestMapper;
import com.cs.roomdbapi.model.*;
import com.cs.roomdbapi.repository.CategoryCodeRepository;
import com.cs.roomdbapi.repository.LanguageRepository;
import com.cs.roomdbapi.repository.PointOfInterestRepository;
import com.cs.roomdbapi.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointOfInterestManagerImpl implements PointOfInterestManager {

    private final PointOfInterestRepository pointOfInterestRepository;

    private final CategoryCodeRepository categoryCodeRepository;

    private final PropertyRepository propertyRepository;

    private final LanguageRepository languageRepository;

    private final DescriptionManager descriptionManager;

    @Override
    public List<PointOfInterest> getAllPOI() {
        List<PointOfInterestEntity> all = pointOfInterestRepository.findAll();

        return PointOfInterestMapper.MAPPER.toListDTO(all);
    }

    @Override
    public PointOfInterest getPOIById(Integer id) {
        PointOfInterestEntity entity = pointOfInterestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(POINT_OF_INTEREST, ID, id));

        return PointOfInterestMapper.MAPPER.toDTO(entity);
    }

    @Override
    public boolean poiNotExistsById(Integer poiId) {
        return !pointOfInterestRepository.existsById(poiId);
    }

    @Override
    public List<PointOfInterest> getAllPointOfInterestByPropertyId(Integer propertyId) {
        List<PointOfInterestEntity> all = pointOfInterestRepository.findAllByProperty_Id(propertyId);

        return PointOfInterestMapper.MAPPER.toListDTO(all);
    }

    @Override
    public Integer getPropertyIdByPOIId(Integer poiId) {
        return pointOfInterestRepository.getPropertyIdByPOIId(poiId);
    }

    @Override
    public void deletePOI(Integer id) {
        PointOfInterestEntity entity = pointOfInterestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(POINT_OF_INTEREST, ID, id));

        pointOfInterestRepository.delete(entity);
    }

    @Override
    public List<CategoryCode> getAllCategoryCodes() {
        List<CategoryCodeEntity> all = categoryCodeRepository.findAll();

        return CategoryCodeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public PointOfInterest addPOI(PointOfInterestSaveRequest poi) {

        PointOfInterestEntity entity = PointOfInterestMapper.MAPPER.toEntity(poi);

        PropertyEntity propertyEntity = propertyRepository.findById(poi.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, poi.getPropertyId()));
        entity.setProperty(propertyEntity);

        if (poi.getCategoryCodeId() != null) {
            CategoryCodeEntity categoryCodeEntity = categoryCodeRepository.findById(poi.getCategoryCodeId())
                    .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_CODE, ID, poi.getCategoryCodeId()));
            entity.setCategoryCode(categoryCodeEntity);
        }

        if (poi.getLanguageId() != null) {
            LanguageEntity languageEntity = languageRepository.findById(poi.getLanguageId())
                    .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, poi.getLanguageId()));
            entity.setLanguage(languageEntity);
        }

        PointOfInterestEntity save = pointOfInterestRepository.save(entity);
        log.info("PointOfInterest added: '{}'", save.toString());

        return PointOfInterestMapper.MAPPER.toDTO(save);
    }

    @Override
    public PointOfInterest updatePOI(Integer id, PointOfInterestSaveRequest poi) {
        PointOfInterestEntity entity = pointOfInterestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(POINT_OF_INTEREST, ID, id));

        if (poi.getPropertyId() != null) {
            if (!entity.getProperty().getId().equals(poi.getPropertyId())) {
                PropertyEntity propertyEntity = propertyRepository.findById(poi.getPropertyId())
                        .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, poi.getPropertyId()));
                entity.setProperty(propertyEntity);
            }
        }

        if (poi.getCategoryCodeId() == null) {
            entity.setCategoryCode(null);
        } else {
            CategoryCodeEntity categoryCodeEntity = categoryCodeRepository.findById(poi.getCategoryCodeId())
                    .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_CODE, ID, poi.getCategoryCodeId()));
            entity.setCategoryCode(categoryCodeEntity);
        }

        if (poi.getLanguageId() == null) {
            entity.setLanguage(null);
        } else {
            LanguageEntity languageEntity = languageRepository.findById(poi.getLanguageId())
                    .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, poi.getLanguageId()));
            entity.setLanguage(languageEntity);
        }

        entity.setName(poi.getName());
        entity.setDistance(poi.getDistance());
        entity.setDistanceUnit(poi.getDistanceUnit());

        PointOfInterestEntity save = pointOfInterestRepository.save(entity);
        log.info("Point Of Interest with id '{}' updated to: '{}'", entity.getId(), entity.toString());

        return PointOfInterestMapper.MAPPER.toDTO(save);
    }

    @Override
    @Transactional
    public Description addPOIDescription(Integer poiId, DescriptionSave descriptionToSave) {
        DescriptionEntity savedDescription = descriptionManager.createDescription(descriptionToSave, DEFAULT_POI_DESCRIPTION_TYPE_CODE);

        PointOfInterestEntity poiEntity = pointOfInterestRepository.findById(poiId)
                .orElseThrow(() -> new ResourceNotFoundException(POINT_OF_INTEREST, ID, poiId));

        poiEntity.getDescriptions().add(savedDescription);
        pointOfInterestRepository.save(poiEntity);

        return DescriptionMapper.MAPPER.toDTO(savedDescription);
    }

    @Override
    public Integer getPOIIdByDescriptionId(Integer descriptionId) {
        return pointOfInterestRepository.getPOIIdByDescriptionId(descriptionId);
    }

}
