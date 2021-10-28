package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.IdentifierSource;

import java.util.List;

public interface IdentifierSourceManager {

    List<IdentifierSource> getAll();

    IdentifierSource getById(Integer id);

    IdentifierSource getByAbbreviation(String code);
}
