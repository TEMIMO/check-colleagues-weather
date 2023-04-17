package com.ultrachecker.checkcolleaguesweather.dto;

public enum UpdateStatus {
    SUCCESS(1L),
    ERROR(2L);

    private final long statusCode;

    UpdateStatus(long statusCode) {
        this.statusCode = statusCode;
    }

    public long getStatusCode() {
        return this.statusCode;
    }

}
