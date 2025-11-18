package com.vulcanium.ecommerce.microcommerce.dao;

import com.vulcanium.ecommerce.microcommerce.model.Product;

import java.util.List;

public interface ProductDAO {

    List<Product> findAll();

    Product findById(int id);

    Product save(Product product);
}
