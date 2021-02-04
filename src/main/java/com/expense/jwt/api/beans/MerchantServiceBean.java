package com.expense.jwt.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantServiceBean {
    private String merchantId;
    private String serviceName;
    private String price;
    private String tax_gst_percentage;
    private String description;
    private String otherDetails;

    @Override
    public String toString() {
        return "MerchantServiceBean{" +
                "merchantid='" + merchantId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", price='" + price + '\'' +
                ", tax_gst_percentage='" + tax_gst_percentage + '\'' +
                ", description='" + description + '\'' +
                ", otherDetails='" + otherDetails + '\'' +
                '}';
    }
}
