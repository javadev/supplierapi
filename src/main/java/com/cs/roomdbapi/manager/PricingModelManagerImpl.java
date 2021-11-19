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

import java.util.ArrayList;
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
                StandardPricingModelEntity spmEntity = getStandardPricingModelEntity(request.getStandardPricingModel());
                recordId = spmEntity.getId();

                standardPricingModel = StandardPricingModelMapper.MAPPER.toDTO(spmEntity);

                break;
            case PRICING_MODEL_CODE_DRV: // Process Derived pricing model
                DerivedPricingModelEntity dpmEntity = getDerivedPricingModelEntity(request.getDerivedPricingModel());
                recordId = dpmEntity.getId();

                derivedPricingModel = DerivedPricingModelMapper.MAPPER.toDTO(dpmEntity);

                break;
            case PRICING_MODEL_CODE_OCC: // Process Occupancy based pricing model
                OccupancyBasedPricingModelEntity obPmEntity = getOccupancyBasedPricingModelEntity(request.getOccupancyBasedPricingModel());
                recordId = obPmEntity.getId();

                occupancyBasedPricingModel = OccupancyBasedPricingModelMapper.MAPPER.toDTO(obPmEntity);

                break;
            case PRICING_MODEL_CODE_LEN: // Process Length of stay pricing model
                LengthOfStayPricingModelEntity losPmEntity = getLengthOfStayPricingModelEntity(request.getLengthOfStayPricingModel());
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
        result.setStandardPricingModel(standardPricingModel);
        result.setDerivedPricingModel(derivedPricingModel);
        result.setOccupancyBasedPricingModel(occupancyBasedPricingModel);
        result.setLengthOfStayPricingModel(lengthOfStayPricingModel);

        return result;
    }

    private LengthOfStayPricingModelEntity getLengthOfStayPricingModelEntity(LengthOfStayPricingModel lengthOfStayPricingModel) {
        if (lengthOfStayPricingModel == null) {
            throw new BadRequestException("Pricing model should not be blank for provided pricing model type.");
        }

        LengthOfStayPricingModelEntity losPmEntity = LengthOfStayPricingModelMapper.MAPPER.toEntity(lengthOfStayPricingModel);
        losPmEntity = lengthOfStayPricingModelRepository.save(losPmEntity);
        return losPmEntity;
    }

    private OccupancyBasedPricingModelEntity getOccupancyBasedPricingModelEntity(OccupancyBasedPricingModel occupancyBasedPricingModel) {
        if (occupancyBasedPricingModel == null) {
            throw new BadRequestException("Pricing model should not be blank for provided pricing model type.");
        }

        OccupancyBasedPricingModelEntity obPmEntity = OccupancyBasedPricingModelMapper.MAPPER.toEntity(occupancyBasedPricingModel);
        obPmEntity = occupancyBasedPricingModelRepository.save(obPmEntity);
        return obPmEntity;
    }

    private DerivedPricingModelEntity getDerivedPricingModelEntity(DerivedPricingModel derivedPricingModel) {
        if (derivedPricingModel == null) {
            throw new BadRequestException("Pricing model should not be blank for provided pricing model type.");
        }

        DerivedPricingModelEntity dpmEntity = DerivedPricingModelMapper.MAPPER.toEntity(derivedPricingModel);
        dpmEntity = derivedPricingModelRepository.save(dpmEntity);
        return dpmEntity;
    }

    private StandardPricingModelEntity getStandardPricingModelEntity(StandardPricingModel standardPricingModel) {
        if (standardPricingModel == null) {
            throw new BadRequestException("Pricing model should not be blank for provided pricing model type.");
        }

        StandardPricingModelEntity spmEntity = StandardPricingModelMapper.MAPPER.toEntity(standardPricingModel);
        spmEntity = standardPricingModelRepository.save(spmEntity);
        return spmEntity;
    }

    @Override
    @Transactional
    public List<PricingModel> getAllPricingModelsByPropertyId(Integer propertyId) {
        List<PricingModelEntity> all = pricingModelRepository.findAllByProperty_Id(propertyId);
        List<PricingModel> result = new ArrayList<>();

        for (PricingModelEntity entity : all) {
            PricingModel record = getPricingModel(entity);
            result.add(record);
        }

        return result;
    }

    private PricingModel getPricingModel(PricingModelEntity entity) {
        PricingModel record = PricingModelMapper.MAPPER.toDTO(entity);

        final Integer recordId = entity.getRecordId();
        if (entity.getPricingModelType() != null && recordId != null) {
            switch (entity.getPricingModelType().getCode()) {
                case PRICING_MODEL_CODE_STD: // get and map Standard pricing model
                    StandardPricingModelEntity spmEntity = standardPricingModelRepository.findById(recordId)
                            .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL_STANDARD, ID, recordId));

                    record.setStandardPricingModel(StandardPricingModelMapper.MAPPER.toDTO(spmEntity));

                    break;
                case PRICING_MODEL_CODE_DRV: // get and map Derived pricing model
                    DerivedPricingModelEntity dpmEntity = derivedPricingModelRepository.findById(recordId)
                            .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL_DERIVED, ID, recordId));

                    record.setDerivedPricingModel(DerivedPricingModelMapper.MAPPER.toDTO(dpmEntity));

                    break;
                case PRICING_MODEL_CODE_OCC: // get and map Occupancy based pricing model
                    OccupancyBasedPricingModelEntity obPmEntity = occupancyBasedPricingModelRepository.findById(recordId)
                            .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL_OCCUPANCY_BASED, ID, recordId));

                    record.setOccupancyBasedPricingModel(OccupancyBasedPricingModelMapper.MAPPER.toDTO(obPmEntity));

                    break;
                case PRICING_MODEL_CODE_LEN: // get and map Length of stay pricing model
                    LengthOfStayPricingModelEntity losPmEntity = lengthOfStayPricingModelRepository.findById(recordId)
                            .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL_LENGTH_OF_STAY, ID, recordId));

                    record.setLengthOfStayPricingModel(LengthOfStayPricingModelMapper.MAPPER.toDTO(losPmEntity));

                    break;
            }
        }
        return record;
    }

    @Override
    public PricingModel getPricingModelById(Integer id) {
        PricingModelEntity entity = pricingModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL, ID, id));

        return getPricingModel(entity);
    }

    @Override
    public boolean pricingModelNotExistsById(Integer pricingModelId) {
        return !pricingModelRepository.existsById(pricingModelId);
    }

    @Override
    public Integer getPropertyIdByPricingModelId(Integer pricingModelId) {
        return pricingModelRepository.getPropertyIdByPricingModelId(pricingModelId);
    }

    @Override
    @Transactional
    public void deletePricingModel(Integer id) {
        PricingModelEntity entity = pricingModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL, ID, id));

        // TODO check that this pricing model is not used in any product. Else - error
        
        final Integer recordId = entity.getRecordId();
        if (entity.getPricingModelType() != null && recordId != null) {
            deletePricingModelExactType(entity.getPricingModelType().getCode(), recordId);
        }

        pricingModelRepository.delete(entity);
    }

    private void deletePricingModelExactType(String pricingModelTypeCode, Integer recordId) {
        switch (pricingModelTypeCode) {
            case PRICING_MODEL_CODE_STD: // delete Standard pricing model
                standardPricingModelRepository.deleteById(recordId);

                break;
            case PRICING_MODEL_CODE_DRV: // delete Derived pricing model
                derivedPricingModelRepository.deleteById(recordId);

                break;
            case PRICING_MODEL_CODE_OCC: // delete Occupancy based pricing model
                occupancyBasedPricingModelRepository.deleteById(recordId);

                break;
            case PRICING_MODEL_CODE_LEN: // delete Length of stay pricing model
                lengthOfStayPricingModelRepository.deleteById(recordId);

                break;
        }
    }

    @Override
    public PricingModel updatePricingModel(Integer id, PricingModelUpdateRequest request) {
        PricingModelEntity entity = pricingModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL, ID, id));

        // Update name
        if (request.getName() != null && !entity.getName().equals(request.getName())) {
            if (pricingModelRepository.existsByProperty_IdAndName(entity.getProperty().getId(), request.getName())) {
                throw new BadRequestException(String.format("Pricing Model with name '%s' already exists for property with id '%s'",
                        request.getName(), entity.getProperty().getId()));
            } else {
                entity.setName(request.getName());
            }
        }

        // Update Pricing Model Type
        PricingModelTypeEntity pmTypeEntity;
        String pricingModelTypeCodeForRemove = entity.getPricingModelType().getCode();
        if (request.getPricingModelTypeId() != null && !entity.getPricingModelType().getId().equals(request.getPricingModelTypeId())) {
            pmTypeEntity = pricingModelTypeRepository.findById(request.getPricingModelTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(PRICING_MODEL_TYPE, ID, request.getPricingModelTypeId()));
            entity.setPricingModelType(pmTypeEntity);
        } else {
            pmTypeEntity = entity.getPricingModelType();
        }

        StandardPricingModel standardPricingModel = null;
        DerivedPricingModel derivedPricingModel = null;
        OccupancyBasedPricingModel occupancyBasedPricingModel = null;
        LengthOfStayPricingModel lengthOfStayPricingModel = null;

        if (request.getStandardPricingModel() != null || request.getDerivedPricingModel() != null
                || request.getOccupancyBasedPricingModel() != null || request.getLengthOfStayPricingModel() != null) {

            // Remove old model
            Integer recordId = entity.getRecordId();
            deletePricingModelExactType(pricingModelTypeCodeForRemove, recordId);

            // Create new model
            switch (pmTypeEntity.getCode()) {
                case PRICING_MODEL_CODE_STD: // Process Standard pricing model
                    StandardPricingModelEntity spmEntity = getStandardPricingModelEntity(request.getStandardPricingModel());
                    recordId = spmEntity.getId();

                    standardPricingModel = StandardPricingModelMapper.MAPPER.toDTO(spmEntity);

                    break;
                case PRICING_MODEL_CODE_DRV: // Process Derived pricing model
                    DerivedPricingModelEntity dpmEntity = getDerivedPricingModelEntity(request.getDerivedPricingModel());
                    recordId = dpmEntity.getId();

                    derivedPricingModel = DerivedPricingModelMapper.MAPPER.toDTO(dpmEntity);

                    break;
                case PRICING_MODEL_CODE_OCC: // Process Occupancy based pricing model
                    OccupancyBasedPricingModelEntity obPmEntity = getOccupancyBasedPricingModelEntity(request.getOccupancyBasedPricingModel());
                    recordId = obPmEntity.getId();

                    occupancyBasedPricingModel = OccupancyBasedPricingModelMapper.MAPPER.toDTO(obPmEntity);

                    break;
                case PRICING_MODEL_CODE_LEN: // Process Length of stay pricing model
                    LengthOfStayPricingModelEntity losPmEntity = getLengthOfStayPricingModelEntity(request.getLengthOfStayPricingModel());
                    recordId = losPmEntity.getId();

                    lengthOfStayPricingModel = LengthOfStayPricingModelMapper.MAPPER.toDTO(losPmEntity);

                    break;
            }

            if (recordId == null) {
                throw new CustomException("Error while saving Pricing Model.", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            entity.setRecordId(recordId);
        }

        PricingModelEntity save = pricingModelRepository.save(entity);

        PricingModel result = PricingModelMapper.MAPPER.toDTO(save);
        result.setStandardPricingModel(standardPricingModel);
        result.setDerivedPricingModel(derivedPricingModel);
        result.setOccupancyBasedPricingModel(occupancyBasedPricingModel);
        result.setLengthOfStayPricingModel(lengthOfStayPricingModel);

        return result;
    }

}
