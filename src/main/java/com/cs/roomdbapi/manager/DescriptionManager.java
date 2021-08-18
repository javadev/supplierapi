package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.Description;
import com.cs.roomdbapi.dto.DescriptionSave;
import com.cs.roomdbapi.dto.DescriptionType;
import com.cs.roomdbapi.model.DescriptionEntity;

import java.util.List;

public interface DescriptionManager {

    List<DescriptionType> getAllDescriptionTypes();

    DescriptionEntity createDescription(DescriptionSave descriptionToSave, String descriptionTypeCode);

    Description updateDescription(Integer descriptionId, DescriptionSave descriptionToSave);

    void deleteMediaDescription(Integer mediaId, Integer id);

    void deletePOIDescription(Integer poiId, Integer id);

}
