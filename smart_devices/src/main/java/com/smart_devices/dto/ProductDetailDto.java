package com.smart_devices.dto;

import com.poiji.annotation.ExcelCell;
import com.smart_devices.enums.ProductDetailStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
	@ExcelCell(0)                
    private int productId;  

    @ExcelCell(1)
    private String series;

    @ExcelCell(2)
    private String version;

    @ExcelCell(3)
    private String title;
    
    @ExcelCell(4)
    private int  gpuId;

    @ExcelCell(5)
    private int  storageId;
    
    @ExcelCell(6)
    private int  processorId;
    
    @ExcelCell(7)
    private int  operatingSystemId;
    
    @ExcelCell(8)
    private String size;
    
    @ExcelCell(9)
    private int weight;

    @ExcelCell(10)
    private int stock;
    
    @ExcelCell(11)
    private double price;
    
    @ExcelCell(12)
    private ProductDetailStatus status;
    
    @ExcelCell(13)
    private int ramId;
    
}
