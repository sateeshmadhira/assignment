package com.ess.assaignment.infrastructure.domain.sql.repository;

import com.ess.assaignment.infrastructure.domain.sql.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
}
