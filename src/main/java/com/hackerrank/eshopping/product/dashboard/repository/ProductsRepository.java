package com.hackerrank.eshopping.product.dashboard.repository;

import java.util.List;

import com.hackerrank.eshopping.product.dashboard.model.Product;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends CrudRepository<Product, Long> {

	public List<Product> findByCategory(String category, Sort sort);

	public List<Product> findByCategoryAndAvailability(String category, Boolean availability, Sort sort);

	public List<Product> findAllByOrderByIdAsc();
}