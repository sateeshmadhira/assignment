package com.ess.assaignment.core.dto;
import com.ess.assaignment.core.utils.BillType;
import com.ess.assaignment.core.utils.PayType;
import lombok.Data;

@Data
public class BillingDTO {

    private Long id;
    private Long assignmentIdRef;
    private PayType payType;
    private BillType billType;
    private double payRate;
    private String comments;
}
