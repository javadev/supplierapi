package com.cs.roomdbapi.model;

public enum CapacityType {
    room("room"),
    bed("bed");

    private final String code;

    CapacityType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
