package com.ess.assignment.core.resp;
import lombok.Data;

@Data
public class ApiResponse {

    private boolean success;
    private String message;
    private Object data;
    private PaginationResponse paginationResponse;
    private long count;

    public ApiResponse(boolean success, String message, long count, Object data) {
        this.success = success;
        this.message = message;
        this.count = count;
        this.data = data;
    }

    public ApiResponse(boolean success, String message, Object data, PaginationResponse paginationResponse, long count) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.paginationResponse = paginationResponse;
        this.count = count;
    }

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, Object data, PaginationResponse paginationResponse) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.paginationResponse = paginationResponse;
    }
}

