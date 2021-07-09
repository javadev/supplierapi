package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.Notification;
import com.cs.roomdbapi.model.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NotificationMapper {

    NotificationMapper MAPPER = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "updateTime", source = "last_update")
    Notification toDTO(NotificationEntity e);

    List<Notification> toListDTO(List<NotificationEntity> list);

}
