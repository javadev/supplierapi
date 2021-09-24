package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.AvailabilityMapper;
import com.cs.roomdbapi.mapper.SellableUnitMapper;
import com.cs.roomdbapi.mapper.SellableUnitTypeMapper;
import com.cs.roomdbapi.model.*;
import com.cs.roomdbapi.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellableUnitManagerImpl implements SellableUnitManager {

    private final SellableUnitRepository sellableUnitRepository;

    private final SellableUnitTypeRepository sellableUnitTypeRepository;

    private final PropertyRepository propertyRepository;

    private final LanguageRepository languageRepository;

    private final NameTypeRepository nameTypeRepository;

    private final NameRepository nameRepository;

    private final AvailabilityRepository availabilityRepository;

    @Override
    public List<SellableUnitType> getAllSellableUnitTypes() {
        List<SellableUnitTypeEntity> all = sellableUnitTypeRepository.findAll();

        return SellableUnitTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public SellableUnit addSellableUnit(SellableUnitSaveRequest request) {

        PropertyEntity propertyEntity = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, request.getPropertyId()));

        SellableUnitEntity entity = SellableUnitMapper.MAPPER.toEntity(request);
        entity.setProperty(propertyEntity);

        List<NameSave> names = request.getNames();
        if (names != null && names.size() > 0) {
            List<NameEntity> nameEntities = new ArrayList<>();

            NameTypeEntity nameTypeEntity = nameTypeRepository.findByCode(DEFAULT_SELLABLE_UNIT_NAME_TYPE_CODE)
                    .orElseThrow(() -> new ResourceNotFoundException(NAME_TYPE, CODE, DEFAULT_SELLABLE_UNIT_NAME_TYPE_CODE));

            for (NameSave name : names) {
                if (name.getLanguageId() == null) {
                    throw new BadRequestException("Language Id should not be blank for name.");
                }

                LanguageEntity language = languageRepository.findById(name.getLanguageId())
                        .orElseThrow(() -> new ResourceNotFoundException(LANGUAGE, ID, name.getLanguageId()));

                if (name.getText() == null || name.getText().isBlank()) {
                    throw new BadRequestException(String.format("Name text should not be blank for language '%s'.", language.getName()));
                }

                NameEntity nameEntity = new NameEntity();
                nameEntity.setLanguage(language);
                nameEntity.setText(name.getText());
                nameEntity.setNameType(nameTypeEntity);

                nameEntities.add(nameEntity);
            }

            nameRepository.saveAll(nameEntities);
            entity.setNames(nameEntities);
        }

        if (request.getSellableUnitTypeId() != null) {
            SellableUnitTypeEntity sellableUnitTypeEntity = sellableUnitTypeRepository.findById(request.getSellableUnitTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(SELLABLE_UNIT_TYPE, ID, request.getSellableUnitTypeId()));
            entity.setSellableUnitType(sellableUnitTypeEntity);
        }

        SellableUnitEntity save = sellableUnitRepository.save(entity);

        return SellableUnitMapper.MAPPER.toDTO(save);
    }

    @Override
    public boolean sellableUnitNotExistsById(Integer sellableUnitId) {
        return !sellableUnitRepository.existsById(sellableUnitId);
    }

    @Override
    public Integer getPropertyIdBySellableUnitId(Integer sellableUnitId) {
        return sellableUnitRepository.getPropertyIdBySellableUnitId(sellableUnitId);
    }

    @Override
    @Transactional
    public void deleteSellableUnit(Integer id) {
        SellableUnitEntity sellableUnitEntity = sellableUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SELLABLE_UNIT, ID, id));

        if (sellableUnitEntity.getNames() != null && sellableUnitEntity.getNames().size() > 0 ) {
            nameRepository.deleteAll(sellableUnitEntity.getNames());
        }

        sellableUnitRepository.delete(sellableUnitEntity);  // TODO check that descriptions are removed
    }

    @Override
    public SellableUnit getSellableUnitById(Integer id) {
        SellableUnitEntity entity = sellableUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SELLABLE_UNIT, ID, id));

        return SellableUnitMapper.MAPPER.toDTO(entity);
    }

    @Override
    public List<Availability> getAvailabilitiesBySellableUnitId(Integer sellableUnitId) {
        List<AvailabilityEntity> all = availabilityRepository.findAllBySellableUnitId(sellableUnitId);

        return AvailabilityMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<SellableUnit> getAllSellableUnitsByPropertyId(Integer propertyId) {
        List<SellableUnitEntity> all = sellableUnitRepository.findAllByProperty_Id(propertyId);

        return SellableUnitMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public List<Availability> setSellableUnitAvailabilities(Integer sellableUnitId, List<AvailabilitySave> availabilities) {

        List<AvailabilityEntity> availabilityEntities = new ArrayList<>();
        if (availabilities != null) {
            for (AvailabilitySave availability : availabilities) {

                if (availability.getCountAvailable() == null || availability.getDate() == null) {
                    throw new BadRequestException("Availability count and date are required and should be provided.");
                }

                AvailabilityEntity entity;

                // If availability exists for specified date we will update count for this record
                Optional<AvailabilityEntity> optional = availabilityRepository.findBySellableUnitIdAndDateAndTimeSegment(sellableUnitId,
                        availability.getDate(), availability.getTimeSegment());
                entity = optional.orElseGet(AvailabilityEntity::new);

                entity.setCountAvailable(availability.getCountAvailable());
                if (entity.getDate() == null) {
                    entity.setDate(availability.getDate());
                }
                if (entity.getTimeSegment() == null) {
                    entity.setTimeSegment(availability.getTimeSegment());
                }
                entity.setSellableUnitId(sellableUnitId);

                availabilityEntities.add(entity);
            }
        }

        List<AvailabilityEntity> saveAll = new ArrayList<>();
        if (availabilityEntities.size() > 0) {
            saveAll = availabilityRepository.saveAll(availabilityEntities);
        }

        return AvailabilityMapper.MAPPER.toListDTO(saveAll);
    }

}
                                                                
