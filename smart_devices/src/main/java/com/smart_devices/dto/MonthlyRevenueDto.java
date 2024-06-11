package com.smart_devices.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRevenueDto {
	private int month;
    private int year;
    private double totalRevenue;
    private List<DailyRevenueDto> dailyRevenues;
    
    public MonthlyRevenueDto(int month, int year, double totalRevenue) {
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }
}