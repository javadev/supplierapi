package com.cs.roomdbapi.manager;

import com.cs.roomdbapi.dto.PhoneType;

import java.util.List;

public interface PhoneTypeManager {

    List<PhoneType> getPhoneType();

    PhoneType getPhoneTypeById(Integer id);

}
