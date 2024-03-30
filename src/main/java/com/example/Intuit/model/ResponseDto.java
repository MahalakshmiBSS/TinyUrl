package com.example.Intuit.model;

import java.time.LocalDateTime;

public class ResponseDto {
    private String LongUrl;
    private String tinyUrl;
    private LocalDateTime expirationDate;

    public ResponseDto(String LongUrl, String tinyUrl, LocalDateTime expirationDate) {
        this.LongUrl = LongUrl;
        this.tinyUrl = tinyUrl;
        this.expirationDate = expirationDate;
    }

    public ResponseDto() {
    }

    public String getLongUrl() {
        return LongUrl;
    }

    public void setLongUrl(String LongUrl) {
        this.LongUrl = LongUrl;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "LongUrl='" + LongUrl + '\'' +
                ", tinyUrl='" + tinyUrl + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
