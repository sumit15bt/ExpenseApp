package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private Integer id;
    private String title;
    private Integer serviceid;
    private Integer merchantid;
    private String servicename; // for request only.
    private String merchantname; // for request only.

    private Date expensedate;   //
    private Date expenseentrydate;   //
    private String paymenttype;
    private String cost;
    private String selectpaymentmode;
    private String paymentrefnumber;

    private String exdate;   // from client
    private String amountpaid;
    private String balancelefttopaid;
    private String exentrydate;

    private Date lastupdated;

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", serviceid=" + serviceid +
                ", merchantid=" + merchantid +
                ", servicename='" + servicename + '\'' +
                ", merchantname='" + merchantname + '\'' +
                ", expensedate=" + expensedate +
                ", expenseentrydate=" + expenseentrydate +
                ", paymenttype='" + paymenttype + '\'' +
                ", cost='" + cost + '\'' +
                ", selectpaymentmode='" + selectpaymentmode + '\'' +
                ", paymentrefnumber='" + paymentrefnumber + '\'' +
                ", exdate='" + exdate + '\'' +
                ", amountpaid='" + amountpaid + '\'' +
                ", balancelefttopaid='" + balancelefttopaid + '\'' +
                ", exentrydate='" + exentrydate + '\'' +
                ", lastupdated=" + lastupdated +
                '}';
    }
}
