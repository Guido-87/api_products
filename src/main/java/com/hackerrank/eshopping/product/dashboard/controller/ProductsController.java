package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductRepository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

  private ProductRepository productRepository;

  @PostMapping
  public void add(@Valid Product p, BindingResult result) {
    if (result.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Binding error");
    }
    productRepository.save(p);
  }

  @PutMapping("/{product_id}")
  public void update(@PathVariable("id") Long id, @Valid Product p, BindingResult result) {
    if (result.hasErrors()) {
      p.setId(id);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Binding error");
    }
    productRepository.save(p);
  }

  @GetMapping("/{product_id}")
  public Product findById(@PathVariable("id") Long id) {
    return productRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found: " + id));
  }

  public List<Product> findByCategory(@RequestParam String category) {
    return productRepository.findByCategory(category);
  }

  public List<Product> findByCategoryAndAvailability(@RequestParam String category,
    @RequestParam String availability) {
    return productRepository.findByCategoryAndAvailability(category, availability);
  }

  public Iterable<Product> findAll() {
    return productRepository.findAll();
  }
}
