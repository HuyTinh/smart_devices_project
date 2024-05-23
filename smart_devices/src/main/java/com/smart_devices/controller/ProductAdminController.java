package com.smart_devices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart_devices.model.ProductDetail;
import com.smart_devices.service.ProductDetailService;
import com.smart_devices.service.ProductService;

@Controller
@RequestMapping("cat-phone/admin")
public class ProductAdminController {
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductDetailService productDetailService;
	
	@GetMapping("product/phone-manage")
	public String product_Phone(Model model) {
		List<ProductDetail> productDetailList = productDetailService.findAll();
		model.addAttribute("productDetailList", productDetailList);
		return "page/phone-manage";
	}
	@GetMapping("product/phone-accessories")
	public String product_accessories() {
		return "page/phone-accessories";
	}
	
}
