package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.PredefinedTag;
import com.cs.roomdbapi.mapper.PredefinedTagMapper;
import com.cs.roomdbapi.model.PredefinedTagEntity;
import com.cs.roomdbapi.repository.PredefinedTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PredefinedTagManagerImpl implements PredefinedTagManager {

    private final PredefinedTagRepository predefinedTagRepository;

    @Override
    public List<PredefinedTag> getAllPredefinedTags() {
        List<PredefinedTagEntity> all = predefinedTagRepository.findAll();

        List<PredefinedTag> predefinedTags = PredefinedTagMapper.MAPPER.toListDTO(all);

        predefinedTags = getTree(predefinedTags);

        return predefinedTags;
    }


    private List<PredefinedTag> getTree(List<PredefinedTag> list) {
        Map<Integer, PredefinedTag> dtoMap = new HashMap<>();
        for (PredefinedTag node : list) {
            dtoMap.put(node.getId(), node);
        }
        List<PredefinedTag> resultList = new ArrayList<>();
        for (Map.Entry<Integer, PredefinedTag> entry : dtoMap.entrySet()) {
            PredefinedTag node = entry.getValue();
            if (node.getParentId() == null) {
                // If it is a top-level node, add it directly to the result set
                resultList.add(node);
            } else {
                // If it is not a top-level node, find its parent node and add it to the parent node's child node collection
                if (dtoMap.get(node.getParentId()) != null) {
                    dtoMap.get(node.getParentId()).getChildren().add(node);
                }
            }
        }
        return resultList;
    }

}
