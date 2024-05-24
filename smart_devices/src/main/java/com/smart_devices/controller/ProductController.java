package com.smart_devices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart_devices.service.CartService;
import com.smart_devices.service.ProductDetailService;
import com.smart_devices.service.ProductService;
import com.smart_devices.service.UserService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductDetailService productDetailService;

	@Autowired
	UserService userService;

	@Autowired
	CartService cartService;

	@GetMapping
	public String showProductPage() {
		userService.setLoginUser(userService.findById(2));
		return "redirect:/product/iPhone";
	}

	@GetMapping("/{lineOfProduct}")
	public String showProductPageWithLineOfProduct(@PathVariable("lineOfProduct") String lineOfProduct,
			@RequestParam(name = "sortByPrice", required = false) String sortBy, Model model) {
		if (sortBy != null) {
			if (sortBy.equalsIgnoreCase("ASC")) {
				model.addAttribute("product",
						productService.findByLineOfProduct(lineOfProduct, Sort.by(Sort.Direction.ASC, "price")));
			} else {
				model.addAttribute("product",
						productService.findByLineOfProduct(lineOfProduct, Sort.by(Sort.Direction.DESC, "price")));
			}
		}

		model.addAttribute("product", productService.findByLineOfProduct(lineOfProduct));

		return "page/ProductPage";
	}

	@GetMapping("/productDetail/{id}")
	public String showProductDetailPage(@PathVariable("id") int id, Model model) {
		model.addAttribute(productDetailService.findById(id));
		return "page/ProductDetailPage";
	}

	@GetMapping("/productDetail/add/{productDetailId}")
	public String showProductDetailPage(@PathVariable("productDetailId") int productDetailId) {
		cartService.addToCart(productDetailService.findById(productDetailId));
		return "redirect:/product/productDetail/" + productDetailId;
	}

}
