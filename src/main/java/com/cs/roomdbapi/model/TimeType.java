package com.cs.roomdbapi.model;

public enum TimeType {
    day("day"),
    hour("hour"),
    thirty_minutes("30_minutes"),
    fifteen_minutes("15_minutes");

    private final String code;

    TimeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
