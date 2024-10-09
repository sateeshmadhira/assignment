package com.ess.assaignment.core.dto;
import com.ess.assaignment.core.utils.WorkType;
import lombok.Data;

@Data
public class WorkLocationDTO {
    private Long id;
    private Long linkId;
    private WorkType workType;
    private String country;
    private String city;
    private String address;
    private String reportingManagerName;
    private String reportingManagerEmail;
}
