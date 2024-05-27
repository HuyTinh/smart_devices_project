package com.smart_devices.controller;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.poiji.bind.Poiji;
import com.smart_devices.dto.ProductDetailDto;
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
	
	@GetMapping("product/phone-manage")
	public String product_Phone(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
	    Pageable pageable = PageRequest.of(page, 5);
	    Page<ProductDetail> pageResult = productDetailService.findAllPage(pageable);

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
	@GetMapping("product/phone-accessories/brand")
	public String brand_form() {
		return "page/brand-form";
	}
	@GetMapping("product/phone-accessories/brand/edit")
	public String editAccessories(Model model, @RequestParam("brandid") Integer branid) {
		model.addAttribute("brand", brandService.getById(branid));
		return "page/brand-form";
	}
	@PostMapping("product/phone-accessories/brand/add")
	public String addBrand(@ModelAttribute("brand") Brand brand) {
		brandService.save(brand);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}
	@PostMapping("product/phone-accessories/brand/update")
	public String updateBrand(@ModelAttribute("brand") Brand brand) {
		brandService.save(brand);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}
	@RequestMapping("product/phone-accessories/brand/delete")
	public String deleteBrand(@RequestParam("brandid") Integer brandid) {
		brandService.deleteById(brandid);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}

	
	@ModelAttribute("productDetailList")
	public List<ProductDetail> getProductDetails(){
		return productDetailService.findAll();
	}
	
}
