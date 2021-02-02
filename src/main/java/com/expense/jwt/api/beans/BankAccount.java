package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    private String accountHolderName;
    private String accountNo;
    private String bankName;
    private String ifscCode;
}
