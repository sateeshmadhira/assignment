package com.ess.assignment.infrastructure.domain.sql.service.handler;

import com.ess.assignment.core.dto.AssignmentDTO;
import com.ess.assignment.infrastructure.domain.sql.model.AssignmentEntity;



public interface AssignmentMapper {
    AssignmentEntity toEntity(AssignmentDTO dto);
    AssignmentDTO toDTO(AssignmentEntity entity);
}
