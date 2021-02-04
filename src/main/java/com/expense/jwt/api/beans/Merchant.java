package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {
    private String id;
    private String name;
    private String primaryContactNo;
    private String secondaryContactNo;
    private String gstNo;
    private String address;
    private String state;
    private String cityName;
    private String pinCode;
    private String AccountHolderName;
    private String AccountNo;
    private String BankName;
    private String IfscCode;

}
