package com.cs.roomdbapi.model;

public enum CodeSource {
    ota("ota"),
    booking("booking"),
    cultswitch("cultswitch");

    private final String code;

    CodeSource(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
