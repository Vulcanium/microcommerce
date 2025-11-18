package com.vulcanium.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {

    private int id;
    private String name;
    private int price;

    @JsonFilter("myDynamicFilter")
    private int purchasePrice;
}
