package com.smart_devices.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportPRoductDto {
	private List<String> selectedColumns;
	private List<Integer> selectedIds;
}
