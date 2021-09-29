package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.TimeRange;
import com.cs.roomdbapi.model.TimeRangeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TimeRangeMapper {

    TimeRangeMapper MAPPER = Mappers.getMapper(TimeRangeMapper.class);

    TimeRange toDTO(TimeRangeEntity e);

    List<TimeRange> toListDTO(List<TimeRangeEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    TimeRangeEntity toEntity(TimeRange dto);

}
