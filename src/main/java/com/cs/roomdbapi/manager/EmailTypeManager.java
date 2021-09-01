package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.EmailType;

import java.util.List;

public interface EmailTypeManager {

    List<EmailType> getEmailTypes();

    EmailType getEmailTypeById(Integer id);

}
