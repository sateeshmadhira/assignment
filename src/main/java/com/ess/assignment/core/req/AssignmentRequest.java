package com.ess.assignment.core.req;

import com.ess.assignment.core.dto.AssignmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequest extends ReqFilter{

    private AssignmentDTO assignmentDTO=new AssignmentDTO();

}
