package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.AvailabilityResult;
import com.cs.roomdbapi.dto.Calendar;
import com.cs.roomdbapi.model.CalendarEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CalendarMapper {

    CalendarMapper MAPPER = Mappers.getMapper(CalendarMapper.class);

    @Named("toAvailability")
    AvailabilityResult toAvailability(CalendarEntity e);

    @IterableMapping(qualifiedByName="toAvailability")
    List<AvailabilityResult> toListAvailability(List<CalendarEntity> list);

    @Mapping(target = "id", ignore = true)
    Calendar toDTO(CalendarEntity e);

    List<Calendar> toListDTO(List<CalendarEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    CalendarEntity toEntity(Calendar dto);

}
