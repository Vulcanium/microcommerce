package com.vulcanium.ecommerce.microcommerce.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.vulcanium.ecommerce.microcommerce.dao.ProductDAO;
import com.vulcanium.ecommerce.microcommerce.model.Product;
import com.vulcanium.ecommerce.microcommerce.web.exceptions.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Products", description = "API for CRUD operations on products.")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductDAO productDAO;

    @Operation(summary = "Get the list of products")
    @GetMapping
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

    @Operation(
            summary = "Get a product by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product found"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable final int id) {
        Product productFound = productDAO.findById(id);

        if (productFound == null) {
            throw new ProductNotFoundException("The product with id " + id + " cannot be found.");
        }

        return productFound;
    }

    @Operation(summary = "Create a new product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created")
            })
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product productAdded = productDAO.save(product);

        URI resourceLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(resourceLocation).build();
    }

    @Operation(summary = "Update an existing product")
    @PutMapping
    public void updateProduct(@Valid @RequestBody Product product) {
        productDAO.save(product);
    }

    @Operation(
            summary = "Delete a product by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Product deleted"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable final int id) {
        Product productFound = productDAO.findById(id);

        if (productFound == null) {
            throw new ProductNotFoundException("The product with id " + id + " cannot be found.");
        }

        productDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("test/{priceLimit}")
    public List<Product> queryTests(@PathVariable int priceLimit) {
        return productDAO.findByPriceGreaterThan(priceLimit);
    }
}
