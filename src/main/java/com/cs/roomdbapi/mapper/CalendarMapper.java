package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.model.CalendarEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CalendarMapper {

    CalendarMapper MAPPER = Mappers.getMapper(CalendarMapper.class);

    @Named("toAvailability")
    SUAvailabilityResult toAvailability(CalendarEntity e);

    @IterableMapping(qualifiedByName="toAvailability")
    List<SUAvailabilityResult> toListAvailability(List<CalendarEntity> list);

    @Named("toPrice")
    SUPriceResult toPrice(CalendarEntity e);

    @IterableMapping(qualifiedByName="toPrice")
    List<SUPriceResult> toListPrice(List<CalendarEntity> list);

    @Named("toMinLOS")
    SUMinLOSResult toMinLOS(CalendarEntity e);

    @IterableMapping(qualifiedByName="toMinLOS")
    List<SUMinLOSResult> toListMinLOS(List<CalendarEntity> list);

    @Named("toMaxLOS")
    SUMaxLOSResult toMaxLOS(CalendarEntity e);

    @IterableMapping(qualifiedByName="toMaxLOS")
    List<SUMaxLOSResult> toListMaxLOS(List<CalendarEntity> list);

    @Named("toClosedForSale")
    SUClosedForSaleResult toClosedForSale(CalendarEntity e);

    @IterableMapping(qualifiedByName="toClosedForSale")
    List<SUClosedForSaleResult> toListClosedForSale(List<CalendarEntity> list);

    @Named("toClosedForArrival")
    SUClosedForArrivalResult toClosedForArrival(CalendarEntity e);

    @IterableMapping(qualifiedByName="toClosedForArrival")
    List<SUClosedForArrivalResult> toListClosedForArrival(List<CalendarEntity> list);

    @Named("toClosedForDeparture")
    SUClosedForDepartureResult toClosedForDeparture(CalendarEntity e);

    @IterableMapping(qualifiedByName="toClosedForDeparture")
    List<SUClosedForDepartureResult> toListClosedForDeparture(List<CalendarEntity> list);

    @Mapping(target = "id", ignore = true)
    SUCalendar toDTO(CalendarEntity e);

    List<SUCalendar> toListDTO(List<CalendarEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    CalendarEntity toEntity(SUCalendar dto);

}
