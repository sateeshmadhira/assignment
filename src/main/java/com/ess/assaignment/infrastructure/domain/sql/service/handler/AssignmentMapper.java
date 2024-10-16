package com.ess.assaignment.infrastructure.domain.sql.service.handler;

import com.ess.assaignment.core.dto.AssignmentDTO;
import com.ess.assaignment.infrastructure.domain.sql.model.AssignmentEntity;
import org.mapstruct.Mapper;


public interface AssignmentMapper {
    AssignmentEntity toEntity(AssignmentDTO dto);
    AssignmentDTO toDTO(AssignmentEntity entity);
}
