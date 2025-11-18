package com.vulcanium.ecommerce.microcommerce.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.vulcanium.ecommerce.microcommerce.dao.ProductDAO;
import com.vulcanium.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductDAO productDAO;

    @GetMapping("/products")
    public MappingJacksonValue listProducts() {
        List<Product> productsListed = productDAO.findAll();

        // Filtering rule that tells to ignore the "purchasePrice" property
        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("purchasePrice");

        // Apply the previous filtering rule to all Beans that are annotated with "myDynamicFilter"
        FilterProvider listFilters = new SimpleFilterProvider().addFilter("myDynamicFilter", myFilter);

        // Apply the previous filter to the product list
        MappingJacksonValue productsFiltered = new MappingJacksonValue(productsListed);
        productsFiltered.setFilters(listFilters);

        return productsFiltered;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable final int id) {
        return productDAO.findById(id);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product productAdded = productDAO.save(product);

        if (productAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI resourceLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(resourceLocation).build();
    }
}
