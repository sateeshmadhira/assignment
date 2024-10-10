package com.ess.assaignment.core.dto;

import lombok.Data;

@Data
public class RecruitmentDTO {
    private Long id;
    private Long assignmentIdRef;
    private String accountManager;
    private Long jobId;
    private String recruitmentManager;
}
