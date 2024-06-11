package com.smart_devices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart_devices.dto.MonthlyRevenueDto;
import com.smart_devices.service.OrderService;


@Controller
@RequestMapping("cat-phone/admin")
public class DashBoardController {
	@Autowired
	OrderService orderService;

	public String showDashBoard(@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "year", required = false) Integer year, Model model) {
		List<MonthlyRevenueDto> revenueData = orderService.findMonthlyRevenueWithDailyDetails();
		model.addAttribute("revenueData", revenueData);

		if (month != null && year != null) {
			for (MonthlyRevenueDto monthlyRevenue : revenueData) {
				if (monthlyRevenue.getMonth() == month && monthlyRevenue.getYear() == year) {
					model.addAttribute("dailyRevenueData", monthlyRevenue.getDailyRevenues());
					model.addAttribute("selectedMonth", month);
					model.addAttribute("selectedYear", year);
					break;
				}
			}
		}

		return "DashBoard";
	}
}
