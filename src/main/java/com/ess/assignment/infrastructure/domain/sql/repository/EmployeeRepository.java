package com.ess.assignment.infrastructure.domain.sql.repository;

import com.ess.assignment.infrastructure.domain.sql.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
}
