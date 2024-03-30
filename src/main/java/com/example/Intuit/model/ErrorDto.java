package com.example.Intuit.model;

public class ErrorDto {
    private String status;
    private String error;

    public ErrorDto(String status, String error) {
        this.status = status;
        this.error = error;
    }

    public ErrorDto() {
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {

        return error;
    }

    public void setError(String error) {

        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "status='" + status + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
