package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.model.Product;

import java.util.List;

import com.hackerrank.eshopping.product.dashboard.dao.ProductsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

  @Autowired
  ProductsDAO productsDAO;

  @RequestMapping(method = RequestMethod.POST)
  public void add(@ModelAttribute("product") Product p) {
    productsDAO.add(p);
  }

  @RequestMapping(method = RequestMethod.PUT)
  public void update(@ModelAttribute("product") Product p) {
    productsDAO.update(p);
  }

  @RequestMapping(value = "/get/{product_id}")
  public Product getById(@PathVariable int id) {
    return productsDAO.getById(id);
  }

  public List<Product> getByCategory(@RequestParam String category) {
    return productsDAO.getByCategory(category);
  }

  public List<Product> getByCategoryAndAvailability(@RequestParam String category,
    @RequestParam String availability) {
    return productsDAO.getByCategoryAndAvailability(category, availability);
  }

  public List<Product> getAll() {
    return productsDAO.getAll();
  }
}
