package com.smart_devices.dto;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueProductDTO {
	private int productId;
    private String productTitle;
    private Date orderDate;
    private double revenue;
}
