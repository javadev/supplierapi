package com.cs.roomdbapi.model;

public enum PropertyTypeSource {
    ota("ota"),
    booking("booking"),
    cultswitch("cultswitch");

    private final String code;

    PropertyTypeSource(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
