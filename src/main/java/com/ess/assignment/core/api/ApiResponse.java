package com.ess.assignment.core.api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private boolean success;
    private String message;
    private Object data;
    private PaginationResponse paginationResponse;


}

