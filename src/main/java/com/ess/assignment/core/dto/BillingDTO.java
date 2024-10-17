package com.ess.assignment.core.dto;
import com.ess.assignment.core.utils.BillType;
import com.ess.assignment.core.utils.CurrencyType;
import com.ess.assignment.core.utils.PayType;
import lombok.Data;

import java.util.Date;

@Data
public class BillingDTO {

    private Long id;
    private Long assignmentIdRef;
    private PayType payType;
    private BillType billType;
    private CurrencyType currencyType;
    private double payRate;
    private Date effectiveForm;
    private String comments;
}
