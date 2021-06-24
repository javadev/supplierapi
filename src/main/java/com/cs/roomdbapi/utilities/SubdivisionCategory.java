package com.cs.roomdbapi.utilities;

public enum SubdivisionCategory {
    state("state"),
    district("district"),
    autonomous_community("auto_comm"),
    autonomous_district("auto_dist");

    private final String code;

    SubdivisionCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
