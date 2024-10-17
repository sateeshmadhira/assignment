package com.ess.assignment.infrastructure.domain.sql.model;

import com.ess.assignment.core.utils.BillType;
import com.ess.assignment.core.utils.CurrencyType;
import com.ess.assignment.core.utils.PayType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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

    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(name = "PAY_RATE")
    private double payRate;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "EFFECTIVE_FORM")
    private Date effectiveForm;

    @OneToOne
    @JoinColumn(name = "ASSIGNMENT_ID")
    private AssignmentEntity assignmentEntity;
}
