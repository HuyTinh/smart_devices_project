package com.smart_devices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    int quantity;

    double price;

    String ram;

    String storage;

    String version;

    String processor;

    String gpu;

    String productImage;

    String productTitle;
}
