package com.ess.assignment.infrastructure.domain.sql.repository;

import com.ess.assignment.infrastructure.domain.sql.model.BillingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<BillingEntity,Long> {

}
