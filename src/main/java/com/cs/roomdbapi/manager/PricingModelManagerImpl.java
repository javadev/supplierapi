package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.CustomException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.mapper.*;
import com.cs.roomdbapi.model.*;
import com.cs.roomdbapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cs.roomdbapi.utilities.AppUtils.*;

@Service
@RequiredArgsConstructor
public class PricingModelManagerImpl implements PricingModelManager {

    private final PricingModelTypeRepository pricingModelTypeRepository;

    private final PricingModelRepository pricingModelRepository;

    private final StandardPricingModelRepository standardPricingModelRepository;

    private final DerivedPricingModelRepository derivedPricingModelRepository;

    private final OccupancyBasedPricingModelRepository occupancyBasedPricingModelRepository;

    private final LengthOfStayPricingModelRepository lengthOfStayPricingModelRepository;

    private final PropertyRepository propertyRepository;

    @Override
    public List<PricingModelType> getPricingModelTypes() {
        List<PricingModelTypeEntity> all = pricingModelTypeRepository.findAll();

        return PricingModelTypeMapper.MAPPER.toListDTO(all);
    }

    @Override
    public PricingModelType getPricingModelTypeById(Integer id) {
        PricingModelTypeEntity entity = pricingModelTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL_TYPE, ID, id));

        return PricingModelTypeMapper.MAPPER.toDTO(entity);
    }

    @Override
    @Transactional
    public PricingModel addPricingModel(PricingModelSaveRequest request) {

        PropertyEntity propertyEntity = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(PROPERTY, ID, request.getPropertyId()));

        Integer pricingModelTypeId = request.getPricingModelTypeId();
        if (pricingModelTypeId == null) {
            throw new BadRequestException("Pricing model type id should not be blank.");
        }

        if (pricingModelRepository.existsByProperty_IdAndName(request.getPropertyId(), request.getName())) {
            throw new BadRequestException(String.format("Pricing Model with name '%s' already exists for property with id '%s'",
                    request.getName(), request.getPropertyId()));
        }

        PricingModelTypeEntity pmTypeEntity = pricingModelTypeRepository.findById(pricingModelTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL_TYPE, ID, pricingModelTypeId));

        PricingModelEntity entity = PricingModelMapper.MAPPER.toEntity(request);
        entity.setProperty(propertyEntity);
        entity.setPricingModelType(pmTypeEntity);

        Integer recordId = null;
        StandardPricingModel standardPricingModel = null;
        DerivedPricingModel derivedPricingModel = null;
        OccupancyBasedPricingModel occupancyBasedPricingModel = null;
        LengthOfStayPricingModel lengthOfStayPricingModel = null;

        switch (pmTypeEntity.getCode()) {
            case PRICING_MODEL_CODE_STD: // Process Standard pricing model
                standardPricingModel = request.getStandardPricingModel();
                if (standardPricingModel == null) {
                    throw new BadRequestException("Pricing model should not be blank for provided pricing model type.");
                }

                StandardPricingModelEntity spmEntity = StandardPricingModelMapper.MAPPER.toEntity(standardPricingModel);
                spmEntity = standardPricingModelRepository.save(spmEntity);
                recordId = spmEntity.getId();

                standardPricingModel = StandardPricingModelMapper.MAPPER.toDTO(spmEntity);

                break;
            case PRICING_MODEL_CODE_DRV: // Process Derived pricing model
                derivedPricingModel = request.getDerivedPricingModel();
                if (derivedPricingModel == null) {
                    throw new BadRequestException("Pricing model should not be blank for provided pricing model type.");
                }

                DerivedPricingModelEntity dpmEntity = DerivedPricingModelMapper.MAPPER.toEntity(derivedPricingModel);
                dpmEntity = derivedPricingModelRepository.save(dpmEntity);
                recordId = dpmEntity.getId();

                derivedPricingModel = DerivedPricingModelMapper.MAPPER.toDTO(dpmEntity);

                break;
            case PRICING_MODEL_CODE_OCC: // Process Occupancy based pricing model
                occupancyBasedPricingModel = request.getOccupancyBasedPricingModel();
                if (occupancyBasedPricingModel == null) {
                    throw new BadRequestException("Pricing model should not be blank for provided pricing model type.");
                }

                OccupancyBasedPricingModelEntity obPmEntity = OccupancyBasedPricingModelMapper.MAPPER.toEntity(occupancyBasedPricingModel);
                obPmEntity = occupancyBasedPricingModelRepository.save(obPmEntity);
                recordId = obPmEntity.getId();

                occupancyBasedPricingModel = OccupancyBasedPricingModelMapper.MAPPER.toDTO(obPmEntity);

                break;
            case PRICING_MODEL_CODE_LEN: // Process Length of stay pricing model
                lengthOfStayPricingModel = request.getLengthOfStayPricingModel();
                if (lengthOfStayPricingModel == null) {
                    throw new BadRequestException("Pricing model should not be blank for provided pricing model type.");
                }

                LengthOfStayPricingModelEntity losPmEntity = LengthOfStayPricingModelMapper.MAPPER.toEntity(lengthOfStayPricingModel);
                losPmEntity = lengthOfStayPricingModelRepository.save(losPmEntity);
                recordId = losPmEntity.getId();

                lengthOfStayPricingModel = LengthOfStayPricingModelMapper.MAPPER.toDTO(losPmEntity);

                break;
        }

        if (recordId == null) {
            throw new CustomException("Error while saving Pricing Model.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        entity.setRecordId(recordId);
        PricingModelEntity save = pricingModelRepository.save(entity);

        PricingModel result = PricingModelMapper.MAPPER.toDTO(save);
        if (standardPricingModel != null) {
            result.setStandardPricingModel(standardPricingModel);
        }
        if (derivedPricingModel != null) {
            result.setDerivedPricingModel(derivedPricingModel);
        }
        if (occupancyBasedPricingModel != null) {
            result.setOccupancyBasedPricingModel(occupancyBasedPricingModel);
        }
        if (lengthOfStayPricingModel != null) {
            result.setLengthOfStayPricingModel(lengthOfStayPricingModel);
        }

        return result;
    }

}
