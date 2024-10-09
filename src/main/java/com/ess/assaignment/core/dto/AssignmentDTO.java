package com.ess.assaignment.core.dto;
import com.ess.assaignment.core.utils.HiringType;
import com.ess.assaignment.core.utils.PlacementType;
import com.ess.assaignment.core.utils.Status;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AssignmentDTO {

    private Long assignmentId;
    private String assignmentCode;
    private String assignmentTitle;
    private Date startDate;
    private Long subscrptionId;
    private Date endDate;
    private Status status;
    private PlacementType placementType;
    private HiringType hiringType;
    private WorkLocationDTO workLocationDTO ;
    private RecruitmentDTO recruitmentDTO ;
    private BillingDTO billingDTO ;
    private List<EmployeeDTO> employeeDTOS;

}
