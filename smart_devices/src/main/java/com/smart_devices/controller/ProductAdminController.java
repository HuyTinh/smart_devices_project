package com.smart_devices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart_devices.model.Brand;
import com.smart_devices.model.ProductDetail;
import com.smart_devices.service.BrandService;
import com.smart_devices.service.CategoryService;
import com.smart_devices.service.GpuService;
import com.smart_devices.service.OperatingSystemService;
import com.smart_devices.service.ProcessorService;
import com.smart_devices.service.ProductDetailService;
import com.smart_devices.service.ProductService;
import com.smart_devices.service.RamService;
import com.smart_devices.service.StorageService;

@Controller
@RequestMapping("cat-phone/admin")
public class ProductAdminController {
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductDetailService productDetailService;
	
	@Autowired
	BrandService brandService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	GpuService gpuService;
	
	@Autowired
	OperatingSystemService operatingSystemService;
	
	@Autowired
	ProcessorService processorService;
	
	@Autowired
	RamService ramService;
	
	@Autowired
	StorageService storageService;
	
	@GetMapping("product/phone-manage")
	public String product_Phone(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
	    Pageable pageable = PageRequest.of(page, 5);
	    Page<ProductDetail> pageResult = productDetailService.findAll(pageable);

	    model.addAttribute("productDetailList", pageResult.getContent());
	    model.addAttribute("page", pageResult);

	    return "page/phone-manage";
	}
	@GetMapping("product/phone-form")
	public String phone_form(Model model) {
		model.addAttribute("brandList", brandService.findAll());
		model.addAttribute("categoryList", categoryService.findAll());
		model.addAttribute("gpuList", gpuService.findAll());
		model.addAttribute("operatingSystemList", operatingSystemService.findAll());
		model.addAttribute("processorList", processorService.findAll());
		model.addAttribute("ramList", ramService.findAll());
		model.addAttribute("storageList", storageService.findAll());
		return "/page/phone-form";
	}
	@GetMapping("product/phone-accessories")
	public String product_accessories(Model model) {
		model.addAttribute("brandList", brandService.findAll());
		model.addAttribute("categoryList", categoryService.findAll());
		model.addAttribute("gpuList", gpuService.findAll());
		model.addAttribute("operatingSystemList", operatingSystemService.findAll());
		model.addAttribute("processorList", processorService.findAll());
		model.addAttribute("ramList", ramService.findAll());
		model.addAttribute("storageList", storageService.findAll());
		return "page/phone-accessories";
	}
	@ModelAttribute("brand")
	public Brand getBrand() {
		return Brand.builder().build();
	}
	@GetMapping("product/phone-accessories/edit")
	public String editAccessories(Model model, @RequestParam("brandid") Integer branid) {
		model.addAttribute("brand", brandService.getById(branid));
		return "page/accessories-form";
	}
	
	
}
