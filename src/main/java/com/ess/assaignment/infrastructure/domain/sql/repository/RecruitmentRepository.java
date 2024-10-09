package com.ess.assaignment.infrastructure.domain.sql.repository;

import com.ess.assaignment.infrastructure.domain.sql.model.RecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long> {

}
