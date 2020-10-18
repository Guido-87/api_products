package com.hackerrank.eshopping.product.dashboard.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hackerrank.eshopping.product.dashboard.model.Product;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ProductsDAO {
  JdbcTemplate template;

  public void setTemplate(JdbcTemplate template) {
    this.template = template;
  }

  public int add(Product p) {
    return template.update("insert into Product(id, name, category, retail_price, discounted_price, availability)"
      + "values('" + p.getId() + "','" + p.getName() + "','" + p.getCategory() + "','" + p.getRetailPrice() +
      "','" + p.getDiscountedPrice() + "','" + p.getAvailability() +"')");
  }

    public int update(Product p) {
    return template.update("update Product set retail_price ='" + p.getRetailPrice() + "', discounted_price ='"
    + p.getDiscountedPrice() + "', availability ='" + p.getAvailability() + "'where id='" + p.getId() + "'");
  }

  public Product getById(int id) {
    return template.queryForObject("select * from Product where id =?", new Object[]{id},
      new BeanPropertyRowMapper<Product>(Product.class));
  }

  public List<Product> getByCategory(String category) {
    return template.query("select * from Product where category = ?", new Object[]{category},
      new RowMapper<Product>() {
        public Product mapRow(ResultSet rs, int row) throws SQLException {
          Product p = new Product();
          p.setId(rs.getLong(1));
          p.setName(rs.getString(2));
          p.setCategory(rs.getString(3));
          p.setRetailPrice(rs.getDouble(4));
          p.setDiscountedPrice(rs.getDouble(5));
          p.setAvailability(rs.getBoolean(6));
          return p;
        }
    });
  }

  public List<Product> getByCategoryAndAvailability(String category, String availability) {
    return template.query("select * from Product where category = ? and availability = ?", new Object[]{category,
      availability}, new RowMapper<Product>() {
        public Product mapRow(ResultSet rs, int row) throws SQLException {
          Product p = new Product();
          p.setId(rs.getLong(1));
          p.setName(rs.getString(2));
          p.setCategory(rs.getString(3));
          p.setRetailPrice(rs.getDouble(4));
          p.setDiscountedPrice(rs.getDouble(5));
          p.setAvailability(rs.getBoolean(6));
          return p;
        }
    });
  }

  public List<Product> getAll() {
    return template.query("select * from Product", new RowMapper<Product>() {
      public Product mapRow(ResultSet rs, int row) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong(1));
        p.setName(rs.getString(2));
        p.setCategory(rs.getString(3));
        p.setRetailPrice(rs.getDouble(4));
        p.setDiscountedPrice(rs.getDouble(5));
        p.setAvailability(rs.getBoolean(6));
        return p;
      }
    });
  }
}