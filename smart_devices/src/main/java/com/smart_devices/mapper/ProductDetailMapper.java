package com.smart_devices.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.smart_devices.dto.ProductDetailDto;
import com.smart_devices.model.ProductDetail;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductDetailMapper {
	

	
}
