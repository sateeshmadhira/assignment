package com.ess.assaignment.core.req;

import com.ess.assaignment.core.dto.AssignmentDTO;
import com.ess.assaignment.core.dto.BillingDTO;
import com.ess.assaignment.core.dto.RecruitmentDTO;
import com.ess.assaignment.core.dto.WorkLocationDTO;
import com.ess.assaignment.core.utils.HiringType;
import com.ess.assaignment.core.utils.PlacementType;
import com.ess.assaignment.core.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequest extends ReqFilter{

    private AssignmentDTO assignmentDTO=new AssignmentDTO();

}
