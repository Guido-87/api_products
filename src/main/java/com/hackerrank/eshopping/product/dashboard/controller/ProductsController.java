package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductsRepository;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

	private final ProductsRepository productsRepository;

	@Autowired
	public ProductsController(final ProductsRepository productRepository) {
		this.productsRepository = productRepository;
	}

//	Validate if id exists
	@PostMapping
	public boolean add(@Valid @RequestBody Product p, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Binding error");
		}
		productsRepository.save(p);
		return true;
	}

//	TODO & merge with add
	@PutMapping("/{id}")
	public boolean update(@PathVariable("id") Long id, @Valid @RequestBody Product p, BindingResult result) {
		if (result.hasErrors() || !productsRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Binding error");
		}
		productsRepository.save(p);
		return true;
	}

//	OK
	@GetMapping("/{id}")
	public Product findById(@PathVariable("id") String id) {
		return productsRepository.findById(Long.valueOf(id))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found: " + id));
	}

//	OK
	@GetMapping(params = "category")
	public List<Product> findByCategory(@RequestParam String category) {
		Sort sort = Sort.by(Sort.Order.desc("availability"), Sort.Order.asc("discountedPrice"), Sort.Order.asc("id"));
		return productsRepository.findByCategory(category, sort);
	}
	
//	OK
	@GetMapping(params = { "category", "availability" })
	public List<Product> findByCategoryAndAvailability(@RequestParam String category,
			@RequestParam Boolean availability) {
		Sort sort = Sort.by(Sort.Order.asc("discountPercentage"), Sort.Order.asc("discountedPrice"),
				Sort.Order.asc("id"));
		return productsRepository.findByCategoryAndAvailability(category, availability, sort);
	}

//	OK 
	@GetMapping
	public List<Product> findAll() {
		return productsRepository.findAllByOrderByIdAsc();
	}
}
