package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBean {
    private String date;
    private String expenseTitle;
    private String merchantName;
    private String serviceName;
}
