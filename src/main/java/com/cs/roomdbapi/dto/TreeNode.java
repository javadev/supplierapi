package com.cs.roomdbapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class TreeNode<T> {

    public Integer id;

    @Schema(description = "Parent Id", example = "Parent Id")
    private Integer parentId;

    @Schema(description = "Child tags", example = "Child tags")
    private List<T> children = new ArrayList<>();

}
