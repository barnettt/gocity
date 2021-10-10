package com.gocity.leisure.pass.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gocity.leisure.pass.dto.CategoryDto;
import com.gocity.leisure.pass.dto.ProductDto;
import com.gocity.leisure.pass.entities.Category;
import com.gocity.leisure.pass.entities.Product;

public abstract class GoCityServiceTestParent {

    protected List<ProductDto> getProductDtoList(LocalDateTime dateTime) {
        List<ProductDto> products = new ArrayList<>();
        ProductDto prod1 = ProductDto.builder().id(3)
                .categoryId(3)
                .creationDate(dateTime)
                .name("GoCity Super Product")
                .description("Gives super powers to the one drinks it")
                .lastPurchasedDate(dateTime)
                .updatedDate(dateTime).category(CategoryDto.builder().id(4)
                        .categoryName("test category")
                        .creationDate(dateTime).build()).build();
        ProductDto prod2 = ProductDto.builder().id(3)
                .categoryId(3)
                .creationDate(dateTime)
                .name("GoCity Super Product")
                .description("Gives super powers to the one drinks it")
                .lastPurchasedDate(dateTime)
                .updatedDate(dateTime).category(CategoryDto.builder().id(4)
                        .categoryName("test category")
                        .creationDate(dateTime).build()).build();
        products.add(prod1);
        products.add(prod2);
        return products;
    }

    protected List<Product> getProductList(LocalDateTime dateTime) {
        List<Product> products = new ArrayList<>();
        Category category = new Category(3, "test category", dateTime);
        Product prod1 = new Product("3", "GoCity Super Product",
                "Gives super powers to the one drinks it", "3", dateTime, dateTime, dateTime, category);
        Product prod2 = new Product("3", "GoCity Super Product",
                "Gives super powers to the one drinks it", "3", dateTime, dateTime, dateTime, category);
        products.add(prod1);
        products.add(prod2);
        return products;
    }
}
