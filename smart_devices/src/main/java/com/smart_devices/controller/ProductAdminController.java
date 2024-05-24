package com.smart_devices.controller;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import com.poiji.bind.Poiji;
import com.smart_devices.dto.ProductDetailDto;
import com.smart_devices.model.ProductDetail;
import com.smart_devices.service.GpuService;
import com.smart_devices.service.OperatingSystemService;
import com.smart_devices.service.ProcessorService;
import com.smart_devices.service.ProductDetailService;
import com.smart_devices.service.ProductService;
import com.smart_devices.service.RamService;
import com.smart_devices.service.StorageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;


@Controller
@RequestMapping("cat-phone/admin")
public class ProductAdminController {

	@Autowired
	ProductService productService;
	
	@Autowired
	ProductDetailService productDetailService;
	
	@Autowired
	GpuService gpuService;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	ProcessorService processorService;
	
	@Autowired
	OperatingSystemService operatingSystemService;
	
	@Autowired
	RamService ramService;

	@PostMapping("product/phone-manage")
	public String productImportExcel(@RequestPart("importExcel") Part productExcelFile, HttpServletRequest request){
		if(productExcelFile  != null && productExcelFile.getSize() > 0) {
			
			String path = "upload/" + productExcelFile.getSubmittedFileName();
			
			String fileName = request.getServletContext().getRealPath(path);
			
			List<ProductDetailDto> productDetailExcels = Poiji.fromExcel(new File(fileName), ProductDetailDto.class);
			
			List<ProductDetail> productDetails = productDetailExcels.stream().map((pdDto) -> ProductDetail.builder()
				.product(productService.findById(pdDto.getProductId()))
				.gpu(gpuService.findById(pdDto.getGpuId()))
				.ram(ramService.findById(pdDto.getRamId()))
				.storage(storageService.findById(pdDto.getStorageId()))
				.processor(processorService.findById(pdDto.getProcessorId()))
				.operatingSystem(operatingSystemService.findById(pdDto.getOperatingSystemId()))
				.status(pdDto.getStatus())
				.size(pdDto.getSize())
				.title(pdDto.getTitle())
				.weight(pdDto.getWeight())
				.price(pdDto.getPrice())
				.stock(pdDto.getStock())
				.series(pdDto.getSeries())
				.version(pdDto.getVersion())
				.build()).collect(Collectors.toList()); 
			
			productDetailService.saveAll(productDetails);
		}
		return "redirect:/cat-phone/admin/product/phone-manage";
	}
	
	
	@GetMapping("product/phone-accessories/brand")
	public String brand_form() {
		return "page/brand-form";
	}

	
	@ModelAttribute("productDetailList")
	public List<ProductDetail> getProductDetails(){
		return productDetailService.findAll();
	}
	
}
