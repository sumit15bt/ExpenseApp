package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDetailBean {
    private String name;
    private String primaryContactNo;
    private String secondaryContactNo;
    private String gstNo;
    private String address;
    private String state;
    private String cityName;
    private String pinCode;
    private String accountHolderName;
    private String accountNo;
    private String bankName;
    private String ifscCode;
}
