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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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

        List<CalendarEntity> calendar = calendarRepository.findAllBySellableUnitId(id);
        if (calendar.size() > 0) {
            calendarRepository.deleteAll(calendar);
        }

        List<SUCapacityEntity> capacity = suCapacityRepository.findAllBySellableUnitId(id);
        if (capacity.size() > 0) {
            suCapacityRepository.deleteAll(capacity);
        }

        // TODO remove su attributes

        sellableUnitRepository.delete(sellableUnitEntity);
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
    @Transactional
    public SellableUnit getOrCreateSellableUnitBySupplierUnitId(String supplierUnitId, Integer propertyId) {
        Optional<SellableUnitEntity> optional = sellableUnitRepository.findBySupplierUnitId(supplierUnitId);

        SellableUnitEntity entity;
        if (optional.isPresent()) {
            entity = optional.get();
        } else {
            PropertyEntity property = propertyRepository.findById(propertyId)
                    .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, propertyId));

            entity = new SellableUnitEntity();
            entity.setProperty(property);
            entity.setSupplierUnitId(supplierUnitId);

            SellableUnitEntity save = sellableUnitRepository.save(entity);
            log.info("Sellable unit added: '{}'", save.toString());
        }

        return SellableUnitMapper.MAPPER.toDTO(entity);
    }

    @Override
    public List<SUAvailabilityResult> getAvailabilitiesBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitIdAndCountAvailableIsNotNull(sellableUnitId);

        return CalendarMapper.MAPPER.toListAvailability(all);
    }

    @Override
    public List<SellableUnit> getAllSellableUnitsByPropertyId(Integer propertyId) {
        List<SellableUnitEntity> all = sellableUnitRepository.findAllByProperty_Id(propertyId);

        return SellableUnitMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public List<SUAvailabilityResult> setAvailabilitiesToSellableUnit(Integer sellableUnitId, List<SUAvailabilitySave> availabilities) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (availabilities != null) {
            for (SUAvailabilitySave availability : availabilities) {

                if (availability.getCountAvailable() == null || availability.getDate() == null) {
                    throw new BadRequestException("Availability count and date are required and should be provided.");
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, availability.getDate(), availability.getTimeSegment());
                entity.setCountAvailable(availability.getCountAvailable());

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListAvailability(saveAll);
    }

    @Override
    @Transactional
    public List<SUAvailabilityResult> setAvailabilitiesToSellableUnitForDateRange(SUAvailabilityDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getCountAvailable() == null) {
            throw new BadRequestException("Availability count is required and should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());
            entity.setCountAvailable(request.getCountAvailable());

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

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
    public List<SUPriceResult> getPricesBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitIdAndPriceIsNotNull(sellableUnitId);

        return CalendarMapper.MAPPER.toListPrice(all);
    }

    @Override
    @Transactional
    public List<SUPriceResult> setPricesToSellableUnit(Integer sellableUnitId, List<SUPriceSave> prices) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (prices != null) {
            for (SUPriceSave price : prices) {
                if (price.getPrice() == null || price.getDate() == null) {
                    throw new BadRequestException("Price and date are required and should be provided.");
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, price.getDate(), price.getTimeSegment());
                entity.setPrice(price.getPrice());

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListPrice(saveAll);
    }

    @Override
    @Transactional
    public List<SUPriceResult> setPricesToSellableUnitForDateRange(SUPriceDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getPrice() == null) {
            throw new BadRequestException("Price is required and should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());
            entity.setPrice(request.getPrice());

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListPrice(saveAll);
    }

    @Override
    public List<SUCommissionResult> getCommissionsBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitIdAndCommissionIsNotNull(sellableUnitId);

        return CalendarMapper.MAPPER.toListCommission(all);
    }

    @Override
    @Transactional
    public List<SUCommissionResult> setCommissionsToSellableUnit(Integer sellableUnitId, List<SUCommissionSave> commissions) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (commissions != null) {
            for (SUCommissionSave commission : commissions) {
                if (commission.getCommission() == null || commission.getDate() == null) {
                    throw new BadRequestException("Commission and date are required and should be provided.");
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, commission.getDate(), commission.getTimeSegment());
                entity.setCommission(commission.getCommission());

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListCommission(saveAll);
    }

    @Override
    @Transactional
    public List<SUCommissionResult> setCommissionsToSellableUnitForDateRange(SUCommissionDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getCommission() == null) {
            throw new BadRequestException("Commission is required and should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());
            entity.setCommission(request.getCommission());

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListCommission(saveAll);
    }

    private List<CalendarEntity> saveCalendarAll(List<CalendarEntity> calendarEntities) {
        List<CalendarEntity> saveAll = new ArrayList<>();
        if (calendarEntities.size() > 0) {
            saveAll = calendarRepository.saveAll(calendarEntities);
        }
        return saveAll;
    }

    /*
    * Check that at least one week day is set and it's true. Otherwise throw exception.
    */
    private void validateWeekDays(SUDateRangeRequest request) {
        if ((request.getMonday() == null || !request.getMonday())
                && (request.getTuesday() == null || !request.getTuesday())
                && (request.getWednesday() == null || !request.getWednesday())
                && (request.getThursday() == null || !request.getThursday())
                && (request.getFriday() == null || !request.getFriday())
                && (request.getSaturday() == null || !request.getSaturday())
                && (request.getSunday() == null || !request.getSunday())
        ) {
            throw new BadRequestException("Week days validation fail. At least one week day should be provided and should be set to true.");
        }
    }

    private boolean checkDayOfWeek(SUDateRangeRequest request, DayOfWeek dayOfWeek) {
        if (dayOfWeek.equals(DayOfWeek.MONDAY) && (request.getMonday() == null || !request.getMonday())) {
            return true;
        }
        if (dayOfWeek.equals(DayOfWeek.TUESDAY) && (request.getTuesday() == null || !request.getTuesday())) {
            return true;
        }
        if (dayOfWeek.equals(DayOfWeek.WEDNESDAY) && (request.getWednesday() == null || !request.getWednesday())) {
            return true;
        }
        if (dayOfWeek.equals(DayOfWeek.THURSDAY) && (request.getThursday() == null || !request.getThursday())) {
            return true;
        }
        if (dayOfWeek.equals(DayOfWeek.FRIDAY) && (request.getFriday() == null || !request.getFriday())) {
            return true;
        }
        if (dayOfWeek.equals(DayOfWeek.SATURDAY) && (request.getSaturday() == null || !request.getSaturday())) {
            return true;
        }
        if (dayOfWeek.equals(DayOfWeek.SUNDAY) && (request.getSunday() == null || !request.getSunday())) {
            return true;
        }
        return false;
    }

    private void validateDates(SUDateRangeRequest request) {
        if (request.getStartDate() == null || request.getEndDate() == null ) {
            throw new BadRequestException("Start date and End date are required and should be provided.");
        }
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BadRequestException("Provided Start date is after End date. Start date should be before or equals to End date.");
        }
    }

    @Override
    public List<SUMinLOSResult> getMinLOSRecordsBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitIdAndMinLOSIsNotNull(sellableUnitId);

        return CalendarMapper.MAPPER.toListMinLOS(all);
    }

    @Override
    @Transactional
    public List<SUMinLOSResult> setMinLOSRecordsToSellableUnit(Integer sellableUnitId, List<SUMinLOSSave> minLOSRecords) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (minLOSRecords != null) {
            for (SUMinLOSSave minLOSRecord : minLOSRecords) {
                if (minLOSRecord.getMinLOS() == null || minLOSRecord.getDate() == null) {
                    throw new BadRequestException("Minimum length of stay and date are required and should be provided.");
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, minLOSRecord.getDate(), minLOSRecord.getTimeSegment());
                entity.setMinLOS(minLOSRecord.getMinLOS());

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListMinLOS(saveAll);
    }

    @Override
    @Transactional
    public List<SUMinLOSResult> setMinLOSRecordsToSellableUnitForDateRange(SUMinLOSDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getMinLOS() == null) {
            throw new BadRequestException("Minimum length of stay is required and should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());
            entity.setMinLOS(request.getMinLOS());

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListMinLOS(saveAll);
    }

    @Override
    public List<SUMaxLOSResult> getMaxLOSRecordsBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitIdAndMaxLOSIsNotNull(sellableUnitId);

        return CalendarMapper.MAPPER.toListMaxLOS(all);
    }

    @Override
    @Transactional
    public List<SUMaxLOSResult> setMaxLOSRecordsToSellableUnit(Integer sellableUnitId, List<SUMaxLOSSave> maxLOSRecords) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (maxLOSRecords != null) {
            for (SUMaxLOSSave maxLOSRecord : maxLOSRecords) {
                if (maxLOSRecord.getMaxLOS() == null || maxLOSRecord.getDate() == null) {
                    throw new BadRequestException("Maximum length of stay and date are required and should be provided.");
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, maxLOSRecord.getDate(), maxLOSRecord.getTimeSegment());
                entity.setMaxLOS(maxLOSRecord.getMaxLOS());

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListMaxLOS(saveAll);
    }

    @Override
    @Transactional
    public List<SUMaxLOSResult> setMaxLOSRecordsToSellableUnitForDateRange(SUMaxLOSDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getMaxLOS() == null) {
            throw new BadRequestException("Maximum length of stay is required and should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());
            entity.setMaxLOS(request.getMaxLOS());

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListMaxLOS(saveAll);
    }

    @Override
    public List<SUClosedForSaleResult> getClosedForSaleRecordsBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitIdAndClosedForSaleIsNotNull(sellableUnitId);

        return CalendarMapper.MAPPER.toListClosedForSale(all);
    }

    @Override
    @Transactional
    public List<SUClosedForSaleResult> setClosedForSaleRecordsToSellableUnit(Integer sellableUnitId, List<SUClosedForSaleSave> closedForSaleRecords) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (closedForSaleRecords != null) {
            for (SUClosedForSaleSave closedForSaleRecord : closedForSaleRecords) {
                if (closedForSaleRecord.getClosedForSale() == null || closedForSaleRecord.getDate() == null) {
                    throw new BadRequestException("Closed for sale and date are required and should be provided.");
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, closedForSaleRecord.getDate(), closedForSaleRecord.getTimeSegment());
                entity.setClosedForSale(closedForSaleRecord.getClosedForSale());

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListClosedForSale(saveAll);
    }

    @Override
    @Transactional
    public List<SUClosedForSaleResult> setClosedForSaleRecordsToSellableUnitForDateRange(SUClosedForSaleDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getClosedForSale() == null) {
            throw new BadRequestException("Closed for sale is required and should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());
            entity.setClosedForSale(request.getClosedForSale());

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListClosedForSale(saveAll);
    }

    @Override
    public List<SUClosedForArrivalResult> getClosedForArrivalRecordsBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitIdAndClosedForArrivalIsNotNull(sellableUnitId);

        return CalendarMapper.MAPPER.toListClosedForArrival(all);
    }

    @Override
    @Transactional
    public List<SUClosedForArrivalResult> setClosedForArrivalRecordsToSellableUnit(Integer sellableUnitId, List<SUClosedForArrivalSave> closedForArrivalRecords) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (closedForArrivalRecords != null) {
            for (SUClosedForArrivalSave closedForArrivalRecord : closedForArrivalRecords) {
                if (closedForArrivalRecord.getClosedForArrival() == null || closedForArrivalRecord.getDate() == null) {
                    throw new BadRequestException("Closed for arrival and date are required and should be provided.");
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, closedForArrivalRecord.getDate(), closedForArrivalRecord.getTimeSegment());
                entity.setClosedForArrival(closedForArrivalRecord.getClosedForArrival());

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListClosedForArrival(saveAll);
    }

    @Override
    @Transactional
    public List<SUClosedForArrivalResult> setClosedForArrivalRecordsToSellableUnitForDateRange(SUClosedForArrivalDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getClosedForArrival() == null) {
            throw new BadRequestException("Closed for arrival is required and should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());
            entity.setClosedForArrival(request.getClosedForArrival());

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListClosedForArrival(saveAll);
    }

    @Override
    public List<SUClosedForDepartureResult> getClosedForDepartureRecordsBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitIdAndClosedForDepartureIsNotNull(sellableUnitId);

        return CalendarMapper.MAPPER.toListClosedForDeparture(all);
    }

    @Override
    @Transactional
    public List<SUClosedForDepartureResult> setClosedForDepartureRecordsToSellableUnit(Integer sellableUnitId, List<SUClosedForDepartureSave> closedForDepartureRecords) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (closedForDepartureRecords != null) {
            for (SUClosedForDepartureSave closedForDepartureRecord : closedForDepartureRecords) {
                if (closedForDepartureRecord.getClosedForDeparture() == null || closedForDepartureRecord.getDate() == null) {
                    throw new BadRequestException("Closed for departure and date are required and should be provided.");
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, closedForDepartureRecord.getDate(), closedForDepartureRecord.getTimeSegment());
                entity.setClosedForDeparture(closedForDepartureRecord.getClosedForDeparture());

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListClosedForDeparture(saveAll);
    }

    @Override
    @Transactional
    public List<SUClosedForDepartureResult> setClosedForDepartureRecordsToSellableUnitForDateRange(SUClosedForDepartureDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getClosedForDeparture() == null) {
            throw new BadRequestException("Closed for departure is required and should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());
            entity.setClosedForDeparture(request.getClosedForDeparture());

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListClosedForDeparture(saveAll);
    }

    private CalendarEntity getCalendarEntity(Integer sellableUnitId, LocalDate date, LocalTime timeSegment) {
        CalendarEntity entity;

        // If calendar record exists for specified date we will update data for this record
        Optional<CalendarEntity> optional = calendarRepository.findBySellableUnitIdAndDateAndTimeSegment(sellableUnitId, date, timeSegment);

        entity = optional.orElseGet(CalendarEntity::new);
        if (entity.getDate() == null) {
            entity.setDate(date);
        }
        if (entity.getTimeSegment() == null) {
            entity.setTimeSegment(timeSegment);
        }
        if (entity.getSellableUnitId() == null) {
            entity.setSellableUnitId(sellableUnitId);
        }

        return entity;
    }

    @Override
    public List<SUCalendar> getCalendarRowsBySellableUnitId(Integer sellableUnitId) {
        List<CalendarEntity> all = calendarRepository.findAllBySellableUnitId(sellableUnitId);

        return CalendarMapper.MAPPER.toListDTO(all);
    }

    @Override
    @Transactional
    public List<SUCalendar> setCalendarRowsToSellableUnit(Integer sellableUnitId, List<SUCalendar> calendars) {

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        if (calendars != null) {
            for (SUCalendar calendar : calendars) {

                if (calendar.getDate() == null) {
                    throw new BadRequestException("Calendar date is required and should be provided.");
                }
                if (calendar.getCountAvailable() == null && calendar.getPrice() == null
                        && calendar.getMinLOS() == null && calendar.getMaxLOS() == null
                        && calendar.getClosedForSale() == null && calendar.getClosedForArrival() == null
                        && calendar.getClosedForDeparture() == null && calendar.getCommission() == null){
                    throw new BadRequestException(String.format(
                            "All data fields for calendar entry are empty for date: %s. At least one field should be provided.",
                            calendar.getDate()
                    ));
                }

                CalendarEntity entity = getCalendarEntity(sellableUnitId, calendar.getDate(), calendar.getTimeSegment());

                // set(for new) or rewrite(for existing) calendar data fields
                if (calendar.getCountAvailable() != null) {
                    entity.setCountAvailable(calendar.getCountAvailable());
                }
                if (calendar.getPrice() != null) {
                    entity.setPrice(calendar.getPrice());
                }
                if (calendar.getCommission() != null) {
                    entity.setCommission(calendar.getCommission());
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

                calendarEntities.add(entity);
            }
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListDTO(saveAll);
    }

    @Override
    @Transactional
    public List<SUCalendar> setCalendarRowsToSellableUnitForDateRange(SUCalendarDateRangeRequest request) {

        validateDates(request);
        validateWeekDays(request);
        if (request.getCountAvailable() == null && request.getPrice() == null
                && request.getMinLOS() == null && request.getMaxLOS() == null
                && request.getClosedForSale() == null && request.getClosedForArrival() == null
                && request.getClosedForDeparture() == null && request.getCommission() == null){
            throw new BadRequestException("All data fields for calendar entry are empty. At least one field should be provided.");
        }

        List<CalendarEntity> calendarEntities = new ArrayList<>();
        for (LocalDate date = request.getStartDate(); (date.isBefore(request.getEndDate()) || date.equals(request.getEndDate())); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (checkDayOfWeek(request, dayOfWeek)) continue;

            CalendarEntity entity = getCalendarEntity(request.getSellableUnitId(), date, request.getTimeSegment());

            // set(for new) or rewrite(for existing) calendar data fields
            if (request.getCountAvailable() != null) {
                entity.setCountAvailable(request.getCountAvailable());
            }
            if (request.getPrice() != null) {
                entity.setPrice(request.getPrice());
            }
            if (request.getCommission() != null) {
                entity.setCommission(request.getCommission());
            }
            if (request.getMinLOS() != null) {
                entity.setMinLOS(request.getMinLOS());
            }
            if (request.getMaxLOS() != null) {
                entity.setMaxLOS(request.getMaxLOS());
            }
            if (request.getClosedForSale() != null) {
                entity.setClosedForSale(request.getClosedForSale());
            }
            if (request.getClosedForArrival() != null) {
                entity.setClosedForArrival(request.getClosedForArrival());
            }
            if (request.getClosedForDeparture() != null) {
                entity.setClosedForDeparture(request.getClosedForDeparture());
            }

            calendarEntities.add(entity);
        }

        List<CalendarEntity> saveAll = saveCalendarAll(calendarEntities);

        return CalendarMapper.MAPPER.toListDTO(saveAll);
    }

}
                                                                
