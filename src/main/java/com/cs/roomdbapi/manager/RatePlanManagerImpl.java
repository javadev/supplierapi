package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.RatePlan;
import com.cs.roomdbapi.dto.RatePlanSaveRequest;
import com.cs.roomdbapi.dto.RatePlanUpdateRequest;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.RatePlanMapper;
import com.cs.roomdbapi.model.PropertyEntity;
import com.cs.roomdbapi.model.RatePlanEntity;
import com.cs.roomdbapi.repository.PropertyRepository;
import com.cs.roomdbapi.repository.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatePlanManagerImpl implements RatePlanManager {

    private final RatePlanRepository ratePlanRepository;

    private final PropertyRepository propertyRepository;

    @Override
    public List<RatePlan> getRatePlans() {
        List<RatePlanEntity> all = ratePlanRepository.findAll();

        return RatePlanMapper.MAPPER.toListDTO(all);
    }

    @Override
    public List<RatePlan> getAllRatePlansForProperty(Integer propertyId) {
        List<RatePlanEntity> all = ratePlanRepository.findAllByProperty_Id(propertyId);

        return RatePlanMapper.MAPPER.toListDTO(all);
    }

    @Override
    public RatePlan getRatePlanById(Integer id) {
        RatePlanEntity entity = ratePlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RATE_PLAN, ID, id));

        return RatePlanMapper.MAPPER.toDTO(entity);
    }

    @Override
    public RatePlan addRatePlan(RatePlanSaveRequest request) {
        if (ratePlanRepository.existsByProperty_IdAndName(request.getPropertyId(), request.getName())) {
            throw new BadRequestException(String.format("Rate Plan with name '%s' already exists for property with id '%s'",
                    request.getName(), request.getPropertyId()));
        }

        RatePlanEntity entity = RatePlanMapper.MAPPER.saveRequestToEntity(request);

        if (request.getParentId() != null) {
            RatePlanEntity parent = ratePlanRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException(RATE_PLAN, ID, request.getParentId()));

            entity.setParent(parent);
        }

        PropertyEntity propertyEntity = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, request.getPropertyId()));

        entity.setProperty(propertyEntity);

        // TODO validate all 5 policies, when they are implemented.
        // Check that they exists for propertyId
        // Update mapper

        RatePlanEntity save = ratePlanRepository.save(entity);
        log.info("REQUEST_ID: {}. Rate Plan added: '{}'", MDC.get("REQUEST_ID"), save.toString());

        return RatePlanMapper.MAPPER.toDTO(save);
    }

    @Override
    public RatePlan updateRatePlan(Integer id, RatePlanUpdateRequest request) {
        RatePlanEntity entity = ratePlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RATE_PLAN, ID, id));

        if (!request.getName().equals(entity.getName())) {
            if (ratePlanRepository.existsByProperty_IdAndName(entity.getProperty().getId(), request.getName())) {
                throw new BadRequestException(String.format("Rate Plan with name '%s' already exists for property with id '%s'",
                        request.getName(), entity.getProperty().getId()));
            }
            entity.setName(request.getName());
        }

        if (request.getParentId() != null) {
            if (!request.getParentId().equals(entity.getParent().getId())) {
                RatePlanEntity parent = ratePlanRepository.findById(request.getParentId())
                        .orElseThrow(() -> new ResourceNotFoundException(RATE_PLAN, ID, request.getParentId()));

                entity.setParent(parent);
            }
        } else {
            entity.setParent(null);
        }

        entity.setStopSell(request.getStopSell());
        entity.setClosedToArrival(request.getClosedToArrival());
        entity.setClosedToDeparture(request.getClosedToDeparture());

        entity.setMinLengthOfStay(request.getMinLengthOfStay());
        entity.setMaxLengthOfStay(request.getMaxLengthOfStay());

        entity.setPaymentPolicy(request.getPaymentPolicyId());
        entity.setCancellationPolicy(request.getCancellationPolicyId());
        entity.setBookingPolicy(request.getBookingPolicyId());
        entity.setPolicyInfo(request.getPolicyInfoId());
        entity.setPetPolicy(request.getPetPolicyId());

        RatePlanEntity save = ratePlanRepository.save(entity);
        log.info("REQUEST_ID: {}. Rate Plan with id '{}' updated to: '{}'", MDC.get("REQUEST_ID"), entity.getId(), entity.toString());

        return RatePlanMapper.MAPPER.toDTO(save);
    }

    @Override
    public boolean ratePlanNotExistsById(Integer ratePlanId) {
        return !ratePlanRepository.existsById(ratePlanId);
    }

    @Override
    public Integer getPropertyIdByRatePlanId(Integer ratePlanId) {
        return ratePlanRepository.getPropertyIdByRatePlanId(ratePlanId);
    }

    @Override
    @Transactional
    public void deleteRatePlan(Integer id) {
        RatePlanEntity entity = ratePlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RATE_PLAN, ID, id));

        // TODO check that Rate Plan is not attached to Product. If it is - response with error message.

        // Check that Rate plan is not used as a Parent for other Rate Plan
        List<RatePlanEntity> children = ratePlanRepository.findAllByParent_Id(id);
        if (children.size() > 0) {
            throw new BadRequestException(String.format("Not possible to remove. " +
                    "Rate Plan with id '%s' used as a parent Rate Plan for '%s' rate plan(s).", id, children.size()));
        }

        ratePlanRepository.delete(entity);
    }

}
