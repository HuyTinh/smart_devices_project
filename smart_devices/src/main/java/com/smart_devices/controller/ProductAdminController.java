package com.smart_devices.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.poiji.bind.Poiji;
import com.smart_devices.dto.ProductDetailDto;
import com.smart_devices.enums.ProductDetailStatus;
import com.smart_devices.model.Brand;
import com.smart_devices.model.Category;
import com.smart_devices.model.Processor;
import com.smart_devices.model.Product;
import com.smart_devices.model.ProductDetail;
import com.smart_devices.model.ProductImage;
import com.smart_devices.service.BrandService;
import com.smart_devices.service.CategoryService;
import com.smart_devices.service.GpuService;
import com.smart_devices.service.ImageUploadService;
import com.smart_devices.service.OperatingSystemService;
import com.smart_devices.service.ProcessorService;
import com.smart_devices.service.ProductDetailService;
import com.smart_devices.service.ProductImageService;
import com.smart_devices.service.ProductService;
import com.smart_devices.service.RamService;
import com.smart_devices.service.StorageService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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

	@Autowired
	ImageUploadService imageUploadService;

	@Autowired
	ProductImageService productImageService;

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
		return "redirect:/cat-phone/admin/product/phone-manage";
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
	    return "redirect:/cat-phone/admin/product/phone-manage";
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
		return "page/phone-manage";
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

	// Phone
	@PostMapping("product/phone/add")
	public String addProduct(@ModelAttribute("product") Product product) {
		productService.save(product);
		return "redirect:/cat-phone/admin/product/phone-form?productId=" + product.getId();
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
		return "page/addPhoneForm";
	}

	@GetMapping("product/phone/edit")
	public String editAddPhoneForm(Model model, @RequestParam("productId") Integer productId) {
		model.addAttribute("product", productService.findById(productId));
		model.addAttribute("brandList", brandService.findAll());
		model.addAttribute("categoryList", categoryService.findAll());
		return "page/addPhoneForm";
	}

	@PostMapping("product/phone/save")
	public String updateProduct(@ModelAttribute("product") Product product) {
		productService.save(product);
		return "redirect:/cat-phone/admin/product/phone-manage";
	}

	@RequestMapping("product/phone/delete")
	public String deleteProduct(@RequestParam("productId") Integer productId) {
		productService.deleteById(productId);
		return "redirect:/cat-phone/admin/product/phone-manage";
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
		return "redirect:/cat-phone/admin/product/phone-manage";
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
		return "page/phone-form";
	}

	@PostMapping("product/phone-form/save")
	public String updateProductDetail(@ModelAttribute("productDetail") ProductDetail productDetail,
			@RequestParam("files") MultipartFile[] files) {
		productDetail.setStatus(ProductDetailStatus.AVAILABLE);
		List<String> existingImages = productImageService.findImagePathsByProductDetailId(productDetail.getId());
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
		return "redirect:/cat-phone/admin/product/phone-manage";
	}

	@PostMapping("product/phone-form/delete")
	public String deleteProductDetail(@RequestParam("productDetailId") Integer productDetailId) {
		productDetailService.deleteByProductDetailId(productDetailId);
		return "redirect:/cat-phone/admin/product/phone-manage";
	}

	// Brand
	@ModelAttribute("brand")
	public Brand getBrand() {
		return Brand.builder().build();
	}

	@GetMapping("product/phone-accessories/brand")
	public String brand_form() {
		return "page/brand-form";
	}

	@GetMapping("product/phone-accessories/brand/edit")
	public String editBrand(Model model, @RequestParam("brandid") Integer branid) {
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

	// Category
	@ModelAttribute("category")
	public Category getCategory() {
		return Category.builder().build();
	}

	@GetMapping("product/phone-accessories/category")
	public String category_form() {
		return "page/categories-form";
	}

	@GetMapping("product/phone-accessories/category/edit")
	public String editCategory(Model model, @RequestParam("categoryid") Integer categoryid) {
		model.addAttribute("category", categoryService.getById(categoryid));
		return "page/categories-form";
	}

	@PostMapping("product/phone-accessories/category/add")
	public String addCategory(@ModelAttribute("category") Category category) {
		categoryService.save(category);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/category/update")
	public String updateCategory(@ModelAttribute("category") Category category) {
		categoryService.save(category);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/category/delete")
	public String deleteCategory(@RequestParam("categoryid") Integer categoryid) {
		categoryService.deleteById(categoryid);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}

	// Processor
	@ModelAttribute("processor")
	public Processor getProcessor() {
		return Processor.builder().build();
	}

	@GetMapping("product/phone-accessories/processor")
	public String processor_form() {
		return "page/processor-form";
	}

	@GetMapping("product/phone-accessories/processor/edit")
	public String editProcessor(Model model, @RequestParam("processorid") Integer processorid) {
		model.addAttribute("processor", processorService.getById(processorid));
		return "page/processor-form";
	}

	@PostMapping("product/phone-accessories/processor/add")
	public String addProcessor(@ModelAttribute("processor") Processor processor) {
		processorService.save(processor);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}

	@PostMapping("product/phone-accessories/processor/update")
	public String updateProcessor(@ModelAttribute("processor") Processor processor) {
		processorService.save(processor);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}

	@RequestMapping("product/phone-accessories/processor/delete")
	public String deleteProcessor(@RequestParam("processorid") Integer processorid) {
		categoryService.deleteById(processorid);
		return "redirect:/cat-phone/admin/product/phone-accessories";
	}

	@ModelAttribute("productDetailList")
	public List<ProductDetail> getProductDetails() {
		return productDetailService.findAll();
	}

}
