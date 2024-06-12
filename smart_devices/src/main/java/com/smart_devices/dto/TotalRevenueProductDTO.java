package com.smart_devices.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalRevenueProductDTO {
	 private Integer productId;
	    private String title;
	    private Double totalRevenue;
}
