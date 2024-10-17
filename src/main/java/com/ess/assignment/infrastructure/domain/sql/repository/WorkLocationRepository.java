package com.ess.assignment.infrastructure.domain.sql.repository;

import com.ess.assignment.infrastructure.domain.sql.model.WorkLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLocationRepository extends JpaRepository<WorkLocationEntity, Long> {

}