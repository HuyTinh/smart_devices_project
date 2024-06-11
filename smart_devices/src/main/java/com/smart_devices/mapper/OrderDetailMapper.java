package com.smart_devices.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.smart_devices.dto.OrderDetailDto;
import com.smart_devices.model.OrderDetail;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDetailMapper {
	
	default OrderDetailDto toDto(OrderDetail orderDetail) {
		OrderDetailDto orderDetailDto = OrderDetailDto.builder()
				.productImage(orderDetail.getProductDetail().getProductImage().get(0).getImagePath())
				.productTitle(orderDetail.getProductDetail().getTitle())
				.ram(orderDetail.getProductDetail().getRam().getCapacity())
				.storage(orderDetail.getProductDetail().getStorage().getCapacity())
				.version(orderDetail.getProductDetail().getVersion())
				.processor(orderDetail.getProductDetail().getProcessor().getTitle())
				.gpu(orderDetail.getProductDetail().getGpu().getCapacity())
				.quantity(orderDetail.getQuantity())
				.price(orderDetail.getPrice()).build();
		return orderDetailDto;
	}
}
