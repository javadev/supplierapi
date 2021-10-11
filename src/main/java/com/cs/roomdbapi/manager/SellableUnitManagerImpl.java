package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.*;
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

    private final CalendarRepository calendarRepository;

    private final SUCapacityRepository suCapacityRepository;

    private final TimeRangeRepository timeRangeRepository;

    private final DescriptionManager descriptionManager;

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
    public SellableUnit getSellableUnitBySupplierUnitId(String id) {
        SellableUnitEntity entity = sellableUnitRepository.findBySupplierUnitId(id)
                .orElseThrow(() -> new ResourceNotFoundException(SELLABLE_UNIT, SUPPLIER_UNIT_ID, id));

        return SellableUnitMapper.MAPPER.toDTO(entity);
    }

    @Override
    public SellableUnit getOrCreateSellableUnitBySupplierUnitId(String sellableUnitId, Integer propertyId) {
        Optional<SellableUnitEntity> optional = sellableUnitRepository.findBySupplierUnitId(sellableUnitId);

        SellableUnitEntity entity;
        if (optional.isPresent()) {
            entity = optional.get();
        } else {
            PropertyEntity property = propertyRepository.findById(propertyId)
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

            entity = new SellableUnitEntity();
            entity.setProperty(property);
            entity.setSupplierUnitId(sellableUnitId);

            SellableUnitEntity save = sellableUnitRepository.save(entity);
            log.info("Sellable unit added: '{}'", save.toString());
        }

        return SellableUnitMapper.MAPPER.toDTO(entity);
    }

    @Override
    public List<AvailabilityResult> getAvailabilitiesBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitId(sellableUnitId);

        return CalendarMapper.MAPPER.toListAvailability(all);
    }

    @Override
    public List<SellableUnit> getAllSellableUnitsByPropertyId(Integer propertyId) {
        List<SellableUnitEntity> all = sellableUnitRepository.findAllByProperty_Id(propertyId);

        return SellableUnitMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public List<AvailabilityResult> setAvailabilitiesToSellableUnit(Integer sellableUnitId, List<AvailabilitySave> availabilities) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (availabilities != null) {
            for (AvailabilitySave availability : availabilities) {

                if (availability.getCountAvailable() == null || availability.getDate() == null) {
                    throw new BadRequestException("Availability count and date are required and should be provided.");
                }

                CalendarEntity entity;

                // If availability exists for specified date we will update count for this record
                Optional<CalendarEntity> optional = calendarRepository.findBySellableUnitIdAndDateAndTimeSegment(sellableUnitId,
                        availability.getDate(), availability.getTimeSegment());
                entity = optional.orElseGet(CalendarEntity::new);

                entity.setCountAvailable(availability.getCountAvailable());
                if (entity.getDate() == null) {
                    entity.setDate(availability.getDate());
                }
                if (entity.getTimeSegment() == null) {
                    entity.setTimeSegment(availability.getTimeSegment());
                }
                if (entity.getSellableUnitId() == null) {
                    entity.setSellableUnitId(sellableUnitId);
                }

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = new ArrayList<>();
        if (calendarEntities.size() > 0) {
            saveAll = calendarRepository.saveAll(calendarEntities);
        }

        return CalendarMapper.MAPPER.toListAvailability(saveAll);
    }

    @Override
    @Transactional
    public Description addSellableUnitDescription(Integer sellableUnitId, DescriptionSave descriptionToSave) {
        DescriptionEntity savedDescription = descriptionManager.createDescription(descriptionToSave, DEFAULT_SELLABLE_UNIT_DESCRIPTION_TYPE_CODE);

        SellableUnitEntity entity = sellableUnitRepository.findById(sellableUnitId)
                .orElseThrow(() -> new ResourceNotFoundException(SELLABLE_UNIT, ID, sellableUnitId));

        entity.getDescriptions().add(savedDescription);
        sellableUnitRepository.save(entity);

        return DescriptionMapper.MAPPER.toDTO(savedDescription);
    }

    @Override
    public Integer getSellableUnitIdByDescriptionId(Integer descriptionId) {
        return sellableUnitRepository.getSellableUnitIdByDescriptionId(descriptionId);
    }

    @Override
    public List<SUCapacity> getSUCapacityBySellableUnitId(Integer sellableUnitId) {
        List<SUCapacityEntity> all = suCapacityRepository.findAllBySellableUnitId(sellableUnitId);

        return SUCapacityMapper.MAPPER.toListDTO(all);
    }

    @Transactional
    public List<SUCapacity> setOrAddSellableUnitCapacities(Integer sellableUnitId, List<SUCapacity> capacities, boolean removeExisting) {

        if (removeExisting) {
            suCapacityRepository.removeBySellableUnitId(sellableUnitId);
        }

        List<SUCapacityEntity> suCapacityEntities = new ArrayList<>();
        if (capacities != null) {
            for (SUCapacity capacity : capacities) {
                suCapacityEntities.add(prepareCapacityEntity(sellableUnitId, capacity));
            }
        }

        List<SUCapacityEntity> saveAll = new ArrayList<>();
        if (suCapacityEntities.size() > 0) {
            saveAll = suCapacityRepository.saveAll(suCapacityEntities);
        }

        return SUCapacityMapper.MAPPER.toListDTO(saveAll);
    }

    @Override
    public List<SUCapacity> setSellableUnitCapacities(Integer sellableUnitId, List<SUCapacity> capacities) {

        return setOrAddSellableUnitCapacities(sellableUnitId, capacities, true);
    }

    @Override
    public List<SUCapacity> addSellableUnitCapacities(Integer sellableUnitId, List<SUCapacity> capacities) {

        return setOrAddSellableUnitCapacities(sellableUnitId, capacities, false);
    }

    private SUCapacityEntity prepareCapacityEntity(Integer sellableUnitId, SUCapacity capacity) {
        if (capacity.getCapacity() == null || capacity.getCapacity() < 0) {
            throw new BadRequestException("Capacity should be provided and should be zero or positive number.");
        }

        SUCapacityEntity entity = new SUCapacityEntity();
        
        TimeRangeEntity timeRange = null;
        if (capacity.getTimeRange() != null) {
            timeRange = TimeRangeMapper.MAPPER.toEntity(capacity.getTimeRange());
            timeRangeRepository.save(timeRange);
        }

        entity.setSellableUnitId(sellableUnitId);
        entity.setCapacity(capacity.getCapacity());
        entity.setIsBlockout(capacity.getIsBlockout());
        entity.setTimeRange(timeRange);

        return entity;
    }

    @Override
    public List<Calendar> getCalendarRowsBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitId(sellableUnitId);

        return CalendarMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public List<Calendar> setCalendarRowsToSellableUnit(Integer sellableUnitId, List<Calendar> calendars) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (calendars != null) {
            for (Calendar calendar : calendars) {

                if (calendar.getDate() == null) {
                    throw new BadRequestException("Calendar date is required and should be provided.");
                }
                if (calendar.getCountAvailable() == null && calendar.getPrice() == null
                        && calendar.getMinLOS() == null && calendar.getMaxLOS() == null
                        && calendar.getClosedForSale() == null && calendar.getClosedForArrival() == null
                        && calendar.getClosedForDeparture() == null){
                    throw new BadRequestException(String.format(
                            "All data fields for calendar entry are empty for date: %s. At least one field should be provided.",
                            calendar.getDate()
                    ));
                }

                CalendarEntity entity;

                // If availability exists for specified date we will update count for this record
                Optional<CalendarEntity> optional = calendarRepository.findBySellableUnitIdAndDateAndTimeSegment(sellableUnitId,
                        calendar.getDate(), calendar.getTimeSegment());
                entity = optional.orElseGet(CalendarEntity::new);

                // set(for new) or rewrite(for existing) calendar data fields
                if (calendar.getCountAvailable() != null) {
                    entity.setCountAvailable(calendar.getCountAvailable());
                }
                if (calendar.getPrice() != null) {
                    entity.setPrice(calendar.getPrice());
                }
                if (calendar.getMinLOS() != null) {
                    entity.setMinLOS(calendar.getMinLOS());
                }
                if (calendar.getMaxLOS() != null) {
                    entity.setMaxLOS(calendar.getMaxLOS());
                }
                if (calendar.getClosedForSale() != null) {
                    entity.setClosedForSale(calendar.getClosedForSale());
                }
                if (calendar.getClosedForArrival() != null) {
                    entity.setClosedForArrival(calendar.getClosedForArrival());
                }
                if (calendar.getClosedForDeparture() != null) {
                    entity.setClosedForDeparture(calendar.getClosedForDeparture());
                }

                // set fields if calendar entry is new
                if (entity.getDate() == null) {
                    entity.setDate(calendar.getDate());
                }
                if (entity.getTimeSegment() == null) {
                    entity.setTimeSegment(calendar.getTimeSegment());
                }
                if (entity.getSellableUnitId() == null) {
                    entity.setSellableUnitId(sellableUnitId);
                }

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = new ArrayList<>();
        if (calendarEntities.size() > 0) {
            saveAll = calendarRepository.saveAll(calendarEntities);
        }

        return CalendarMapper.MAPPER.toListDTO(saveAll);
    }

}
                                                                
