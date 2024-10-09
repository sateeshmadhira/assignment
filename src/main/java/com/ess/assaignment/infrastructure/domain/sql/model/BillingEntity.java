package com.ess.assaignment.infrastructure.domain.sql.model;

import com.ess.assaignment.core.utils.BillType;
import com.ess.assaignment.core.utils.PayType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ASSIGNMENT_BILLING")
public class BillingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BILLING_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Enumerated(EnumType.STRING)
    private BillType billType;

    @Column(name = "PAY_RATE")
    private double payRate;
    @Column(name = "COMMENTS")
    private String comments;

    @OneToOne
    @JoinColumn(name = "ASSIGNMENT_ID")
    private AssignmentEntity assignmentEntity;
}
