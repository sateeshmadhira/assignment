package com.ess.assignment.infrastructure.domain.sql.service.impl;
import com.ess.assignment.core.dto.*;
import com.ess.assignment.infrastructure.domain.sql.model.*;
import com.ess.assignment.infrastructure.domain.sql.service.handler.AssignmentMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class  AssignmentMapperImpl implements AssignmentMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AssignmentEntity toEntity(AssignmentDTO dto) {
        AssignmentEntity assignmentEntity = modelMapper.map(dto, AssignmentEntity.class);

        // Map BillingDTO -> BillingEntity
        if (dto.getBillingDTO() != null) {
            BillingEntity billingEntity = modelMapper.map(dto.getBillingDTO(), BillingEntity.class);
            billingEntity.setAssignmentEntity(assignmentEntity);  // Set reverse reference
            assignmentEntity.setBillingEntity(billingEntity);
        }

        // Map RecruitmentDTO -> RecruitmentEntity
        if (dto.getRecruitmentDTO() != null) {
            RecruitmentEntity recruitmentEntity = modelMapper.map(dto.getRecruitmentDTO(), RecruitmentEntity.class);
            recruitmentEntity.setAssignmentEntity(assignmentEntity);  // Set reverse reference
            assignmentEntity.setRecruitmentEntity(recruitmentEntity);
        }

        // Map WorkLocationDTO -> WorkLocationEntity
        if (dto.getWorkLocationDTO() != null) {
            WorkLocationEntity workLocationEntity = modelMapper.map(dto.getWorkLocationDTO(), WorkLocationEntity.class);
            workLocationEntity.setAssignmentEntity(assignmentEntity);  // Set reverse reference
            assignmentEntity.setWorkLocationEntity(workLocationEntity);
        }

        // Handle EmployeeDTO list -> EmployeeEntity list
        if (dto.getEmployeeDTOS() != null) {
            List<EmployeeEntity> employeeEntities = new ArrayList<>();
            for (EmployeeDTO employeeDTO : dto.getEmployeeDTOS()) {
                EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
                employeeEntity.setAssignmentEntity(assignmentEntity);  // Set reverse reference
                employeeEntities.add(employeeEntity);
            }
            assignmentEntity.setEmployeeEntity(employeeEntities);  // Set the list of employees
        }

        return assignmentEntity;
    }

    @Override
    public AssignmentDTO toDTO(AssignmentEntity entity) {
        AssignmentDTO assignmentDTO = modelMapper.map(entity, AssignmentDTO.class);

        // Map BillingEntity -> BillingDTO
        if (entity.getBillingEntity() != null) {
            BillingDTO billingDTO = modelMapper.map(entity.getBillingEntity(), BillingDTO.class);
            billingDTO.setAssignmentIdRef(assignmentDTO.getAssignmentId());
            assignmentDTO.setBillingDTO(billingDTO);
        }

        // Map RecruitmentEntity -> RecruitmentDTO
        if (entity.getRecruitmentEntity() != null) {
            RecruitmentDTO recruitmentDTO = modelMapper.map(entity.getRecruitmentEntity(), RecruitmentDTO.class);
            recruitmentDTO.setAssignmentIdRef(assignmentDTO.getAssignmentId());
            assignmentDTO.setRecruitmentDTO(recruitmentDTO);
        }

        // Map WorkLocationEntity -> WorkLocationDTO
        if (entity.getWorkLocationEntity() != null) {
            WorkLocationDTO workLocationDTO = modelMapper.map(entity.getWorkLocationEntity(), WorkLocationDTO.class);
            workLocationDTO.setAssignmentIdRef(assignmentDTO.getAssignmentId());
            assignmentDTO.setWorkLocationDTO(workLocationDTO);
        }

        // Handle EmployeeEntity list -> EmployeeDTO list
        if (entity.getEmployeeEntity() != null) {
            List<EmployeeDTO> employeeDTOList = new ArrayList<>();
            for (EmployeeEntity employeeEntity : entity.getEmployeeEntity()) {
                EmployeeDTO employeeDTO = modelMapper.map(employeeEntity, EmployeeDTO.class);
                employeeDTO.setAssignmentIdRef(assignmentDTO.getAssignmentId());  // Set assignment reference
                employeeDTOList.add(employeeDTO);
            }
            assignmentDTO.setEmployeeDTOS(employeeDTOList);  // Set the list of employees
        }

        return assignmentDTO;
    }
}
