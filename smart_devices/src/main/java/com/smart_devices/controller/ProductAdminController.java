package com.smart_devices.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.smart_devices.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.poiji.bind.Poiji;
import com.smart_devices.enums.OrderStatus;
import com.smart_devices.enums.ProductDetailStatus;
import com.smart_devices.model.Brand;
import com.smart_devices.model.Category;
import com.smart_devices.model.Gpu;
import com.smart_devices.model.OperatingSystem;
import com.smart_devices.model.OrderDetail;
import com.smart_devices.model.Processor;
import com.smart_devices.model.Product;
import com.smart_devices.model.ProductDetail;
import com.smart_devices.model.ProductImage;
import com.smart_devices.model.Ram;
import com.smart_devices.model.Storage;
import com.smart_devices.model.User;
import com.smart_devices.service.BrandService;
import com.smart_devices.service.CategoryService;
import com.smart_devices.service.GpuService;
import com.smart_devices.service.ImageUploadService;
import com.smart_devices.service.OperatingSystemService;
import com.smart_devices.service.OrderService;
import com.smart_devices.service.ProcessorService;
import com.smart_devices.service.ProductDetailService;
import com.smart_devices.service.ProductImageService;
import com.smart_devices.service.ProductService;
import com.smart_devices.service.RamService;
import com.smart_devices.service.StorageService;
import com.smart_devices.service.UserService;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@Controller
@RequestMapping("admin/cat-phone")
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

	@Autowired
	ImageUploadService imageUploadService;

	@Autowired
	ProductImageService productImageService;

	@Autowired
	OrderService orderService;

	@Autowired
	UserService userService;

	@PostMapping("product/phone-manage")
	public String productImportExcel(@RequestPart("importExcel") Part productExcelFile, HttpServletRequest request) {
		if (productExcelFile != null && productExcelFile.getSize() > 0) {

			String path = "upload/" + productExcelFile.getSubmittedFileName();

			String fileName = request.getServletContext().getRealPath(path);

			List<ProductDetailDto> productDetailExcels = Poiji.fromExcel(new File(fileName), ProductDetailDto.class);

			List<ProductDetail> productDetails = productDetailExcels.stream()
					.map((pdDto) -> ProductDetail.builder().product(productService.findById(pdDto.getProductId()))
							.gpu(gpuService.findById(pdDto.getGpuId())).ram(ramService.findById(pdDto.getRamId()))
							.storage(storageService.findById(pdDto.getStorageId()))
							.processor(processorService.findById(pdDto.getProcessorId()))
							.operatingSystem(operatingSystemService.findById(pdDto.getOperatingSystemId()))
							.status(pdDto.getStatus()).size(pdDto.getSize()).title(pdDto.getTitle())
							.weight(pdDto.getWeight()).price(pdDto.getPrice()).stock(pdDto.getStock())
							.series(pdDto.getSeries()).version(pdDto.getVersion()).build())
					.collect(Collectors.toList());

			productDetailService.saveAll(productDetails);
		}
		return "redirect:/admin/cat-phone/product/phone-manage";
	}

	@PostMapping("product/phone-manage/exportExcel")
	public String productExportExcel(@RequestParam Map<String, String> columns) {
		String filePath = "D:\\ListProduct.xlsx";
		List<ProductDetail> productDetails = productDetailService.findAll();
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Product Details");
			Row headerRow = sheet.createRow(0);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Cell sttHeaderCell = headerRow.createCell(0);
			sttHeaderCell.setCellValue("STT");
			sttHeaderCell.setCellStyle(headerCellStyle);

			List<String> selectedColumnNames = new ArrayList<>(columns.keySet());
			for (int i = 0; i < selectedColumnNames.size(); i++) {
				Cell headerCell = headerRow.createCell(i + 1);
				headerCell.setCellValue(selectedColumnNames.get(i));
				headerCell.setCellStyle(headerCellStyle);
			}

			int rowNum = 1;
			for (int i = 0; i < productDetails.size(); i++) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(i + 1);
				ProductDetail productDetail = productDetails.get(i);
				for (int j = 0; j < selectedColumnNames.size(); j++) {
					String columnName = selectedColumnNames.get(j);
					Cell cell = row.createCell(j + 1);
					switch (columnName) {
					case "ID":
						cell.setCellValue(productDetail.getId());
						break;
					case "Product ID":
						cell.setCellValue(productDetail.getProduct().getId());
						break;
					case "Series":
						cell.setCellValue(productDetail.getSeries());
						break;
					case "Version":
						cell.setCellValue(productDetail.getVersion());
						break;
					case "Title":
						cell.setCellValue(productDetail.getTitle());
						break;
					case "GPU":
						cell.setCellValue(productDetail.getGpu().getCapacity());
						break;
					case "Storage":
						cell.setCellValue(productDetail.getStorage().getCapacity());
						break;
					case "Processor":
						cell.setCellValue(productDetail.getProcessor().getTitle());
						break;
					case "Operating System":
						cell.setCellValue(productDetail.getOperatingSystem().getName());
						break;
					case "Size":
						cell.setCellValue(productDetail.getSize());
						break;
					case "Weight":
						cell.setCellValue(productDetail.getWeight());
						break;
					case "Stock":
						cell.setCellValue(productDetail.getStock());
						break;
					case "Price":
						cell.setCellValue(productDetail.getPrice());
						break;
					case "RAM":
						cell.setCellValue(productDetail.getRam().getCapacity());
						break;
					case "Status":
						cell.setCellValue(productDetail.getStatus().toString());
						break;
					default:
						break;

					}
				}
			}
			for (int i = 0; i <= selectedColumnNames.size(); i++) {
				sheet.autoSizeColumn(i);
			}

			try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
				workbook.write(fileOut);
			}

			System.out.println("Excel file has been generated successfully!");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/cat-phone/product/phone-manage";
	}

	@GetMapping("product/phone-manage")
	public String product_Phone(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<ProductDetail> pageResult = productDetailService.findAllPage(pageable);

		model.addAttribute("productDetailList", pageResult.getContent());
		model.addAttribute("page", pageResult);
		model.addAttribute("brandList", brandService.findAll());
		model.addAttribute("categoryList", categoryService.findAll());
		model.addAttribute("productList", productService.findAll());
		return "Page/PhoneManagePage";
	}

	@GetMapping("product/phone-form")
	public String phone_form(Model model, @RequestParam("productId") Integer productId) {
		Product product = productService.findById(productId);
		model.addAttribute("product", product);
		model.addAttribute("brandList", brandService.findAll());
		model.addAttribute("categoryList", categoryService.findAll());
		model.addAttribute("gpuList", gpuService.findAll());
		model.addAttribute("operatingSystemList", operatingSystemService.findAll());
		model.addAttribute("processorList", processorService.findAll());
		model.addAttribute("ramList", ramService.findAll());
		model.addAttribute("storageList", storageService.findAll());
		return "Page/PhonePage";
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
		return "Page/PhoneAccessoriesPage";
	}

	// Phone
	@PostMapping("product/phone/add")
	public String addProduct(@ModelAttribute("product") Product product) {
		productService.save(product);
		return "redirect:/admin/cat-phone/product/phone-form?productId=" + product.getId();
	}

	@ModelAttribute("productDetail")
	public ProductDetail getProductDetail() {
		return ProductDetail.builder().build();
	}

	@ModelAttribute("product")
	public Product getProduct() {
		return Product.builder().build();
	}

	@GetMapping("product/phone/add")
	public String showAddPhoneForm(Model model) {
		model.addAttribute("brandList", brandService.findAll());
		model.addAttribute("categoryList", categoryService.findAll());
		return "Page/AddPhonePage";
	}

	@GetMapping("product/phone/edit")
	public String editAddPhoneForm(Model model, @RequestParam("productId") Integer productId) {
		model.addAttribute("product", productService.findById(productId));
		model.addAttribute("brandList", brandService.findAll());
		model.addAttribute("categoryList", categoryService.findAll());
		return "Page/AddPhonePage";
	}

	@PostMapping("product/phone/save")
	public String updateProduct(@ModelAttribute("product") Product product) {
		productService.save(product);
		return "redirect:/admin/cat-phone/product/phone-manage";
	}

	@RequestMapping("product/phone/delete")
	public String deleteProduct(@RequestParam("productId") Integer productId) {
		productService.deleteById(productId);
		return "redirect:/admin/cat-phone/product/phone-manage";
	}

	@PostMapping("product/phone-form/add")
	public String addProductDetail(@ModelAttribute("productDetail") ProductDetail productDetail,
			@RequestParam("files") MultipartFile[] files, @RequestParam("productId") Integer productId) {
		productDetail.setProduct(productService.findById(productId));
		productDetail.setStatus(ProductDetailStatus.AVAILABLE);
		productDetailService.save(productDetail);
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {
					String imageUrl = imageUploadService.uploadImage(file);
					ProductImage productImage = new ProductImage();
					productImage.setProductDetail(productDetail);
					productImage.setImagePath(imageUrl);
					productImageService.save(productImage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/admin/cat-phone/product/phone-manage";
	}

	@GetMapping("product/phone-form/edit")
	public String editProductDetail(@RequestParam("productDetailId") Integer productDetailId, Model model) {
		model.addAttribute("listImage", productImageService.findImagePathsByProductDetailId(productDetailId));
		model.addAttribute("productDetail", productDetailService.findById(productDetailId));
		model.addAttribute("brandList", brandService.findAll());
		model.addAttribute("categoryList", categoryService.findAll());
		model.addAttribute("gpuList", gpuService.findAll());
		model.addAttribute("operatingSystemList", operatingSystemService.findAll());
		model.addAttribute("processorList", processorService.findAll());
		model.addAttribute("ramList", ramService.findAll());
		model.addAttribute("storageList", storageService.findAll());
		return "Page/PhonePage";
	}

	@PostMapping("product/phone-form/save")
	public String updateProductDetail(@ModelAttribute("productDetail") ProductDetail productDetail, @ModelAttribute("productId") Integer productId, @RequestParam("files") MultipartFile[] files) {
		productDetail.setStatus(ProductDetailStatus.AVAILABLE);
		List<String> existingImages = productImageService.findImagePathsByProductDetailId(productDetail.getId());
		productDetail.setProduct(productService.findById(productId));
		productDetailService.save(productDetail);
		boolean newImagesUploaded = false;
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				newImagesUploaded = true;
				try {
					String imageUrl = imageUploadService.uploadImage(file);
					ProductImage productImage = new ProductImage();
					productImage.setProductDetail(productDetail);
					productImage.setImagePath(imageUrl);
					productImageService.save(productImage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (!newImagesUploaded) {
			for (String imagePath : existingImages) {
				ProductImage productImage = new ProductImage();
				productImage.setProductDetail(productDetail);
				productImage.setImagePath(imagePath);
				productImageService.save(productImage);
			}
		}
		return "redirect:/admin/cat-phone/product/phone-manage";
	}

	@PostMapping("product/phone-form/delete")
	public String deleteProductDetail(@RequestParam("productDetailId") Integer productDetailId) {
		productDetailService.deleteByProductDetailId(productDetailId);
		return "redirect:/admin/cat-phone/product/phone-manage";
	}

	// Brand
	@ModelAttribute("brand")
	public Brand getBrand() {
		return Brand.builder().build();
	}

	@GetMapping("product/phone-accessories/brand")
	public String brand_form() {
		return "Page/BrandPage";
	}

	@GetMapping("product/phone-accessories/brand/edit")
	public String editBrand(Model model, @RequestParam("brandid") Integer branid) {
		model.addAttribute("brand", brandService.getById(branid));
		return "Page/BrandPage";
	}

	@PostMapping("product/phone-accessories/brand/add")
	public String addBrand(@ModelAttribute("brand") Brand brand) {
		brandService.save(brand);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/brand/update")
	public String updateBrand(@ModelAttribute("brand") Brand brand) {
		brandService.save(brand);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/brand/delete")
	public String deleteBrand(@RequestParam("brandid") Integer brandid) {
		brandService.deleteById(brandid);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	// Category
	@ModelAttribute("category")
	public Category getCategory() {
		return Category.builder().build();
	}

	@GetMapping("product/phone-accessories/category")
	public String category_form() {
		return "Page/CategoriesPage";
	}

	@GetMapping("product/phone-accessories/category/edit")
	public String editCategory(Model model, @RequestParam("categoryid") Integer categoryid) {
		model.addAttribute("category", categoryService.getById(categoryid));
		return "Page/CategoriesPage";
	}

	@PostMapping("product/phone-accessories/category/add")
	public String addCategory(@ModelAttribute("category") Category category) {
		categoryService.save(category);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/category/update")
	public String updateCategory(@ModelAttribute("category") Category category) {
		categoryService.save(category);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/category/delete")
	public String deleteCategory(@RequestParam("categoryid") Integer categoryid) {
		categoryService.deleteById(categoryid);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	// Processor
	@ModelAttribute("processor")
	public Processor getProcessor() {
		return Processor.builder().build();
	}

	@GetMapping("product/phone-accessories/processor")
	public String processor_form() {
		return "Page/ProcessorPage";
	}

	@GetMapping("product/phone-accessories/processor/edit")
	public String editProcessor(Model model, @RequestParam("processorid") Integer processorid) {
		model.addAttribute("processor", processorService.getById(processorid));
		return "Page/ProcessorPage";
	}

	@PostMapping("product/phone-accessories/processor/add")
	public String addProcessor(@ModelAttribute("processor") Processor processor) {
		processorService.save(processor);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/processor/update")
	public String updateProcessor(@ModelAttribute("processor") Processor processor) {
		processorService.save(processor);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/processor/delete")
	public String deleteProcessor(@RequestParam("processorid") Integer processorid) {
		categoryService.deleteById(processorid);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@ModelAttribute("productDetailList")
	public List<ProductDetail> getProductDetails() {
		return productDetailService.findAll();
	}

	// Storage
	@ModelAttribute("storage")
	public Storage getStorage() {
		return Storage.builder().build();
	}

	@GetMapping("product/phone-accessories/storage")
	public String storage_form() {
		return "Page/StoragePage";
	}

	@GetMapping("product/phone-accessories/storage/edit")
	public String editStorage(Model model, @RequestParam("storageid") Integer storageid) {
		model.addAttribute("storage", storageService.getById(storageid));
		return "Page/StoragePage";
	}

	@PostMapping("product/phone-accessories/storage/add")
	public String addStorage(@ModelAttribute("storage") Storage storage) {
		storageService.save(storage);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/storage/update")
	public String updateStorage(@ModelAttribute("storage") Storage storage) {
		storageService.save(storage);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/storage/delete")
	public String deleteStorage(@RequestParam("storageid") Integer storageid) {
		storageService.deleteById(storageid);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	// GPU
	@ModelAttribute("gpu")
	public Gpu getGpu() {
		return Gpu.builder().build();
	}

	@GetMapping("product/phone-accessories/gpu")
	public String gpu_form() {
		return "Page/GpuPage";
	}

	@GetMapping("product/phone-accessories/gpu/edit")
	public String editGpu(Model model, @RequestParam("gpuid") Integer gpuid) {
		model.addAttribute("storage", gpuService.getById(gpuid));
		return "Page/GpuPage";
	}

	@PostMapping("product/phone-accessories/gpu/add")
	public String addGpu(@ModelAttribute("gpu") Gpu gpu) {
		gpuService.save(gpu);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/gpu/update")
	public String updateGpu(@ModelAttribute("gpu") Gpu gpu) {
		gpuService.save(gpu);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/gpu/delete")
	public String deleteGpu(@RequestParam("gpuid") Integer gpuid) {
		gpuService.deleteById(gpuid);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	// RAM
	@ModelAttribute("ram")
	public Ram getRam() {
		return Ram.builder().build();
	}

	@GetMapping("product/phone-accessories/ram")
	public String ram_form() {
		return "Page/RamPage";
	}

	@GetMapping("product/phone-accessories/ram/edit")
	public String editRam(Model model, @RequestParam("ramid") Integer ramid) {
		model.addAttribute("ram", ramService.getById(ramid));
		return "Page/RamPage";
	}

	@PostMapping("product/phone-accessories/ram/add")
	public String addRam(@ModelAttribute("ram") Ram ram) {
		ramService.save(ram);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/ram/update")
	public String updateRam(@ModelAttribute("ram") Ram ram) {
		ramService.save(ram);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/ram/delete")
	public String deleteRam(@RequestParam("ramid") Integer ramid) {
		ramService.deleteById(ramid);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	// OperatingSystem
	@ModelAttribute("operatingSystem")
	public OperatingSystem getOperatingSystem() {
		return OperatingSystem.builder().build();
	}

	@GetMapping("product/phone-accessories/operatingSystem")
	public String operatingSystem_form() {
		return "Page/OperatingSystemPage";
	}

	@GetMapping("product/phone-accessories/operatingSystem/edit")
	public String editoperatingSystem(Model model, @RequestParam("operatingSystemid") Integer operatingSystemid) {
		model.addAttribute("operatingSystem", operatingSystemService.getById(operatingSystemid));
		return "Page/OperatingSystemPage";
	}

	@PostMapping("product/phone-accessories/operatingSystem/add")
	public String addRam(@ModelAttribute("operatingSystem") OperatingSystem operatingSystem) {
		operatingSystemService.save(operatingSystem);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/operatingSystem/update")
	public String updateoperatingSystem(@ModelAttribute("operatingSystem") OperatingSystem operatingSystem) {
		operatingSystemService.save(operatingSystem);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/operatingSystem/delete")
	public String deleteoperatingSystem(@RequestParam("operatingSystemid") Integer operatingSystemid) {
		operatingSystemService.deleteById(operatingSystemid);
		return "redirect:/admin/cat-phone/product/phone-accessories";
	}

	// Orders
	@GetMapping("orders")
	public String orders(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "minTotal", required = false) Double minTotal,
			@RequestParam(name = "maxTotal", required = false) Double maxTotal) {

		int pageSize = 10;
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<OrderDetailManageDto> pageResult;
		if ("".equals(status)) {
			pageResult = orderService.searchOrders(search, startDate, endDate, null, minTotal, maxTotal, pageable);
		} else if (search != null || startDate != null || endDate != null || status != null || minTotal != null
				|| maxTotal != null) {
			pageResult = orderService.searchOrders(search, startDate, endDate, OrderStatus.valueOf(status), minTotal,
					maxTotal, pageable);
		} else {
			pageResult = orderService.findAllOrderDetails(pageable);
		}
		model.addAttribute("orderList", pageResult.getContent());
		model.addAttribute("page", pageResult);
		model.addAttribute("search", search);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		model.addAttribute("minTotal", minTotal);
		model.addAttribute("maxTotal", maxTotal);
		model.addAttribute("orderStatuses", OrderStatus.values());
		return "Page/OrdersPage";
	}

	@PostMapping("orders/{orderId}/status")
	public String updateOrderStatus(@PathVariable int orderId, @RequestParam OrderStatus status) {
		orderService.updateOrderStatus(orderId, status);
		return "redirect:/admin/cat-phone/orders";
	}

	// Customer
	@GetMapping("customer")
	public String customer(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "search", required = false) String search) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<User> pageResult;
		if (search != null && !search.isEmpty()) {
			pageResult = userService.searchUsers(search, pageable);
		} else {
			pageResult = userService.findAllPage(pageable);
		}
		model.addAttribute("userList", pageResult.getContent());
		model.addAttribute("page", pageResult);
		model.addAttribute("search", search);
		return "Page/CustomerPage";
	}

	// Report

	@GetMapping("report/revenue")
	public String report(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "minTotal", required = false) Double minTotal,
			@RequestParam(name = "maxTotal", required = false) Double maxTotal) {

		int pageSize = 10;
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<OrderDetailManageDto> pageResult;
		if ("".equals(status)) {
			pageResult = orderService.searchOrders(search, startDate, endDate, null, minTotal, maxTotal, pageable);
		} else if (search != null || startDate != null || endDate != null || status != null || minTotal != null
				|| maxTotal != null) {
			pageResult = orderService.searchOrders(search, startDate, endDate, OrderStatus.valueOf(status), minTotal,
					maxTotal, pageable);
		} else {
			pageResult = orderService.findAllOrderDetails(pageable);
		}
		model.addAttribute("orderList", pageResult.getContent());
		model.addAttribute("page", pageResult);
		model.addAttribute("search", search);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		model.addAttribute("minTotal", minTotal);
		model.addAttribute("maxTotal", maxTotal);
		model.addAttribute("orderStatuses", OrderStatus.values());
		return "Page/ReportRevenuePage";
	}

	@PostMapping("report/revenue")
	public String exportExcelRevenue(@RequestBody ExportRevenueDto requestData) {
		String filePath = "D:/report-revenue.xlsx";
		// Create a new workbook
		Workbook workbook = new XSSFWorkbook();

		// Create a new sheet
		Sheet sheet = workbook.createSheet("Data");

		// Create header row
		Row headerRow = sheet.createRow(0);
		List<String> header = requestData.getSelectedValColumns();
		for (int i = 0; i < header.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(header.get(i));
		}

		// Populate data rows
		List<Map<String, String>> data = requestData.getTableData();
		int rowNum = 1;
		for (Map<String, String> rowData : data) {
			Row row = sheet.createRow(rowNum++);
			int cellNum = 0;
			for (String key : header) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(rowData.getOrDefault(key, ""));
			}
		}
		// Auto size columns
		for (int i = 0; i < header.size(); i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the workbook to a file
		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
			// Xử lý ngoại lệ (nếu cần)
		} finally {
			// Close the workbook to release resources
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/admin/cat-phone/report/revenue";
	}

	@GetMapping("/report/product")
	public String showReportProduct(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(required = false) String title, @RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice, @RequestParam(required = false) Integer minStock,
			@RequestParam(required = false) Integer maxStock, Model model) {
		Pageable pageable = PageRequest.of(page, 10);
		Page<ProductDetail> pageResult;

		if (title != null || minPrice != null || maxPrice != null || minStock != null || maxStock != null) {
			pageResult = productDetailService.searchProductDetails(title, minPrice, maxPrice, minStock, maxStock,
					pageable);
		} else {
			pageResult = productDetailService.findAllPage(pageable);
		}

		model.addAttribute("productDetails", pageResult.getContent());
		model.addAttribute("page", pageResult);
		model.addAttribute("title", title);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		model.addAttribute("minStock", minStock);
		model.addAttribute("maxStock", maxStock);

		return "Page/ReportProductPage";
	}

	@PostMapping("/report/product/exportExcel")
	public String exportExcelReportProduct(@RequestBody ExportPRoductDto requestData) {
		String filePath = "D:\\ListProduct.xlsx";
		List<ProductDetail> productDetails = productDetailService.findAllById(requestData.getSelectedIds());
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Product Details");
			Row headerRow = sheet.createRow(0);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Cell sttHeaderCell = headerRow.createCell(0);
			sttHeaderCell.setCellValue("STT");
			sttHeaderCell.setCellStyle(headerCellStyle);

			List<String> selectedColumnNames = requestData.getSelectedColumns();
			for (int i = 0; i < selectedColumnNames.size(); i++) {
				Cell headerCell = headerRow.createCell(i + 1);
				headerCell.setCellValue(selectedColumnNames.get(i));
				headerCell.setCellStyle(headerCellStyle);
			}

			int rowNum = 1;
			for (int i = 0; i < productDetails.size(); i++) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(i + 1);
				ProductDetail productDetail = productDetails.get(i);
				for (int j = 0; j < selectedColumnNames.size(); j++) {
					String columnName = selectedColumnNames.get(j);
					Cell cell = row.createCell(j + 1);
					switch (columnName) {
					case "ID":
						cell.setCellValue(productDetail.getId());
						break;
					case "Product ID":
						cell.setCellValue(productDetail.getProduct().getId());
						break;
					case "Series":
						cell.setCellValue(productDetail.getSeries());
						break;
					case "Version":
						cell.setCellValue(productDetail.getVersion());
						break;
					case "Title":
						cell.setCellValue(productDetail.getTitle());
						break;
					case "GPU":
						cell.setCellValue(productDetail.getGpu().getCapacity());
						break;
					case "Storage":
						cell.setCellValue(productDetail.getStorage().getCapacity());
						break;
					case "Processor":
						cell.setCellValue(productDetail.getProcessor().getTitle());
						break;
					case "Operating System":
						cell.setCellValue(productDetail.getOperatingSystem().getName());
						break;
					case "Size":
						cell.setCellValue(productDetail.getSize());
						break;
					case "Weight":
						cell.setCellValue(productDetail.getWeight());
						break;
					case "Stock":
						cell.setCellValue(productDetail.getStock());
						break;
					case "Price":
						cell.setCellValue(productDetail.getPrice());
						break;
					case "RAM":
						cell.setCellValue(productDetail.getRam().getCapacity());
						break;
					case "Status":
						cell.setCellValue(productDetail.getStatus().toString());
						break;
					default:
						break;

					}
				}
			}
			for (int i = 0; i <= selectedColumnNames.size(); i++) {
				sheet.autoSizeColumn(i);
			}

			try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
				workbook.write(fileOut);
			}

			System.out.println("Excel file has been generated successfully!");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/cat-phone/report/product";
	}
	@GetMapping("report/product/chart")
	public String showChartProduct(@RequestParam("productDetailId") Integer productDetailId,
			@RequestParam(value = "secondProductId", required = false) Integer secondProductId,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			Model model) {

		List<RevenueProductDTO> revenueData = new ArrayList<>() ;
		List<RevenueProductDTO> secondData = new ArrayList<>();

		if (secondProductId == null) {
			if (startDate != null && endDate != null) {
				revenueData = productDetailService.findDailyRevenue(productDetailId, startDate, endDate);
				model.addAttribute("infoProduct1", productDetailService.findTotalRevenueByProductDetailIdAndDateRange(productDetailId, startDate, endDate));
			} else {
				revenueData = productDetailService.findTotalRevenueByDate(productDetailId);
				model.addAttribute("infoProduct1", productDetailService.findTotalRevenueByProductId(productDetailId));
				
			}
			model.addAttribute("revenueData", revenueData);
		} else {
			if (startDate != null && endDate != null) {
				revenueData = productDetailService.findDailyRevenue(productDetailId, startDate, endDate);
				secondData = productDetailService.findDailyRevenue(secondProductId, startDate, endDate);
				model.addAttribute("infoProduct1", productDetailService.findTotalRevenueByProductDetailIdAndDateRange(productDetailId, startDate, endDate));
				model.addAttribute("infoProduct2", productDetailService.findTotalRevenueByProductDetailIdAndDateRange(secondProductId, startDate, endDate));
			} else {
				revenueData = productDetailService.findTotalRevenueByDate(productDetailId);
				secondData = productDetailService.findTotalRevenueByDate(secondProductId);
				model.addAttribute("infoProduct1", productDetailService.findTotalRevenueByProductId(productDetailId));
				model.addAttribute("infoProduct2", productDetailService.findTotalRevenueByProductId(secondProductId));
			

			}
			model.addAttribute("revenueData", revenueData);
			model.addAttribute("secondData", secondData);
			
		}
		model.addAttribute("productDetails", productDetailService.findAll());
		model.addAttribute("secondProductId", secondProductId);
		model.addAttribute("productDetailId", productDetailId);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "page/ProductChartPage";
	}
 
}
