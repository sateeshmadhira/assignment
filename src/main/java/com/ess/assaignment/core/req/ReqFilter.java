package com.ess.assaignment.core.req;

import org.springframework.data.domain.Sort;

public class ReqFilter {

    private String id;
    private String code;
    private Integer pageSize;
    private Integer page;
    private Integer totalCount;
    private String sortBy;
    private Sort.Direction direction;
    private boolean loginUser;

}
