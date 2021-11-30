package com.cs.roomdbapi.mapper;

import com.cs.roomdbapi.dto.RatePlan;
import com.cs.roomdbapi.dto.RatePlanSaveRequest;
import com.cs.roomdbapi.model.RatePlanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RatePlanMapper {

    RatePlanMapper MAPPER = Mappers.getMapper(RatePlanMapper.class);

    @Mapping(target = "propertyId", source = "property.id")
    RatePlan toDTO(RatePlanEntity e);

    List<RatePlan> toListDTO(List<RatePlanEntity> list);

    @Mapping(target = "paymentPolicy", source = "paymentPolicyId")
    @Mapping(target = "cancellationPolicy", source = "cancellationPolicyId")
    @Mapping(target = "bookingPolicy", source = "bookingPolicyId")
    @Mapping(target = "policyInfo", source = "policyInfoId")
    @Mapping(target = "petPolicy", source = "petPolicyId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "property", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "last_update", ignore = true)
    RatePlanEntity saveRequestToEntity(RatePlanSaveRequest dto);

}
