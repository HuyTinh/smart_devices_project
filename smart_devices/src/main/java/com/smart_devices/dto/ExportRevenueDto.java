package com.smart_devices.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportRevenueDto {
	private List<String> selectedValColumns;
    private List<Map<String, String>> tableData;
}
