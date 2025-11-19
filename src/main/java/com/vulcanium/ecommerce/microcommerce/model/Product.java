package com.vulcanium.ecommerce.microcommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 3, max = 25)
    private String name;

    @Min(value = 1)
    private int price;

    //    @JsonFilter("myDynamicFilter")
    @Column(name = "purchase_price")
    private int purchasePrice;
}
