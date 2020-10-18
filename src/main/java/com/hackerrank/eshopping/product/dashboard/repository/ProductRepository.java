package com.hackerrank.eshopping.product.dashboard.repository;

import java.util.List;

import com.hackerrank.eshopping.product.dashboard.model.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

  public List<Product> findByCategory(String category);

  public List<Product> findByCategoryAndAvailability(String category, String availability);
}