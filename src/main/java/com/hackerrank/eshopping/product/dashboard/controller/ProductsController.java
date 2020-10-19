package com.hackerrank.eshopping.product.dashboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductsRepository;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody Product newProduct, BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Binding error.");
		}
		if (newProduct.getId() != null && productsRepository.existsById(newProduct.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id already exists.");
		}
		Product createdProduct = productsRepository.save(newProduct);
		if (createdProduct != null) {
			return new ResponseEntity<>("Product added succesfully.", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error adding product.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	TODO Merge with add
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody Product updatedProduct,
			BindingResult result) {
		Product productToUpdate = productsRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id " + id + " not found."));
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Binding error.");
		}
		if (updatedProduct.getAvailability() != null) {
			productToUpdate.setAvailability(updatedProduct.getAvailability());
		}
		if (updatedProduct.getDiscountedPrice() != null) {
			productToUpdate.setDiscountedPrice(updatedProduct.getDiscountedPrice());
		}
		if (updatedProduct.getRetailPrice() != null) {
			productToUpdate.setRetailPrice(updatedProduct.getRetailPrice());
		}
		productToUpdate = productsRepository.save(productToUpdate);
		if (productToUpdate != null) {
			return new ResponseEntity<>("Product updated succesfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error updating product.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public String findById(@PathVariable("id") Long id) throws JsonProcessingException {
		Product product = productsRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found."));
		return new ObjectMapper().writeValueAsString(product);
	}

	@GetMapping(params = "category")
	public String findByCategory(@RequestParam String category) throws JsonProcessingException {
		Sort sort = Sort.by(Sort.Order.desc("availability"), Sort.Order.asc("discountedPrice"), Sort.Order.asc("id"));
		List<Product> list = productsRepository.findByCategory(category, sort);
		return new ObjectMapper().writeValueAsString(list);
	}

	@GetMapping(params = { "category", "availability" })
	public String findByCategoryAndAvailability(@RequestParam String category, @RequestParam Boolean availability) throws JsonProcessingException {
		List<Product> list = productsRepository.findByCategoryAndAvailability(category, availability);
		return new ObjectMapper().writeValueAsString(list);
	}

	@GetMapping
	public String findAll() throws JsonProcessingException {
		List<Product> list = productsRepository.findAllByOrderByIdAsc();
		return new ObjectMapper().writeValueAsString(list);
	}
}
