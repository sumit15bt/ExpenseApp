package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDetail {
    private String expenseTitle;
//    private String merchantId;
    private String serviceId;
    private String expenseDate;
    private String paymentType;
    private String cost;
    private String selectPaymentMode;
    private String paymentRefNumber;
    private String entryDate;
}
