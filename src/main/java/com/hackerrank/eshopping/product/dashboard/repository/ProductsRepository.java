package com.hackerrank.eshopping.product.dashboard.repository;

import java.util.List;

import com.hackerrank.eshopping.product.dashboard.model.Product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends CrudRepository<Product, Long> {

	public List<Product> findByCategory(String category, Sort sort);

	@Query(value = "SELECT * FROM Products p WHERE p.category = ?1 AND p.availability = ?2 ORDER BY "
			+ "(retail_price - discounted_price) / retail_price * 100 DESC, p.discounted_price ASC, p.id ASC",
			nativeQuery = true)
	public List<Product> findByCategoryAndAvailability(String category, Boolean availability);

	public List<Product> findAllByOrderByIdAsc();
}