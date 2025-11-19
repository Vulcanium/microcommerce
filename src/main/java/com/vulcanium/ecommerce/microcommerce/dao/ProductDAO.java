package com.vulcanium.ecommerce.microcommerce.dao;

import com.vulcanium.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    Product findById(int id);
    List<Product> findByPriceGreaterThan(int priceLimit);
}
