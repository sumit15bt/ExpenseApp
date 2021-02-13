package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MServices {
    private Integer id;
    private Integer merchantid;
    private String name;
    private String merchantname;
    private String price;
    private String tax_gst_percentage;
    private String description;
    private String otherdetails;
    private Date entrydate;
    private Date lastupdated;
}
