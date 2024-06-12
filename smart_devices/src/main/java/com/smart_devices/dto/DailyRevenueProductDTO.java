package com.smart_devices.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRevenueProductDTO {
	 private Integer productId;
    private Double totalRevenue;
    private String title;
}
