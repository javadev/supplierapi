package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.RatePlan;
import com.cs.roomdbapi.dto.RatePlanSaveRequest;
import com.cs.roomdbapi.dto.RatePlanUpdateRequest;

import java.util.List;

public interface RatePlanManager {

    List<RatePlan> getRatePlans();

    List<RatePlan> getAllRatePlansForProperty(Integer propertyId);

    RatePlan getRatePlanById(Integer id);

    RatePlan addRatePlan(RatePlanSaveRequest request);

    RatePlan updateRatePlan(Integer id, RatePlanUpdateRequest request);

    boolean ratePlanNotExistsById(Integer ratePlanId);

    Integer getPropertyIdByRatePlanId(Integer ratePlanId);

    void deleteRatePlan(Integer id);

}
