package com.ess.assignment.core.api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> implements Serializable {


    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
    private List<T> content= new ArrayList<>();

}

