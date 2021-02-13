package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {
    private Integer id;
    private String name;
    private String primarycontactno;
    private String secondarycontactno;
    private String gstno;
    private String address;
    private String state;
    private String cityname;
    private String pincode;
    private String accountholdername;
    private String accountno;
    private String bankname;
    private String ifsccode;
    private Date entrydate;
    private Date lastupdated;


    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", primarycontactno='" + primarycontactno + '\'' +
                ", secondarycontactno='" + secondarycontactno + '\'' +
                ", gstno='" + gstno + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", cityname='" + cityname + '\'' +
                ", pincode='" + pincode + '\'' +
                ", accountholdername='" + accountholdername + '\'' +
                ", accountno='" + accountno + '\'' +
                ", bankname='" + bankname + '\'' +
                ", ifsccode='" + ifsccode + '\'' +
                ", entrydate=" + entrydate +
                ", lastupdated=" + lastupdated +
                '}';
    }
}
