package com.ess.assignment.infrastructure.domain.sql.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ASSIGNMENT_RECRUITMENT")
public class RecruitmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "ACCOUNT_MANAGER")
    private String accountManager;

    @Column(name = "JOB_ID")
    private Long jobId;

    @Column(name = "RECRUITMENT_MANAGER")
    private String recruitmentManager;

    @Column(name = "External_Recruiters")
    private String externalRecruiters;

    @Column(name = "RECRUITER")
    private String recruiter;


    @OneToOne
    @JoinColumn(name = "ASSIGNMENT_ID")
    private AssignmentEntity assignmentEntity;

}
