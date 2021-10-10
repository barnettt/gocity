package com.gocity.leisure.pass.integration;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gocity.leisure.pass.dto.ProductDto;
import com.gocity.leisure.pass.entities.Category;
import com.gocity.leisure.pass.entities.Product;
import com.gocity.leisure.pass.repository.CategoryRepository;
import com.gocity.leisure.pass.repository.ProductRepository;
import com.gocity.leisure.pass.service.GoCityProductService;
import com.gocity.leisure.pass.service.GoCityServiceTestParent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class GoCityServiceIntegrationTest extends GoCityServiceTestParent {


    @Mock
    ProductRepository repository;
    @Mock
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper mapper;

    GoCityProductService service;

    @BeforeEach
    public void setUp() {
        service = new GoCityProductService(repository, categoryRepository, mapper);
    }

    @DisplayName("I want to be able to sort the products by Name")
    @Test
    void shouldSortProductsByName() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Product> products = getProductList(dateTime);
        products.addAll(addExtraProducts(dateTime));
        when(repository.findAll()).thenReturn(products);
        List<ProductDto> returnedProduct = service.sortProductsByName();
        List<String> returnedNameOrder = returnedProduct
                .stream()
                .map(ProductDto::getName).collect(Collectors.toList());
        List<String> expectedOrder = getExpectedNameOrder();
        assertLinesMatch(returnedNameOrder, expectedOrder);
    }

    private List<String> getExpectedNameOrder() {
        List<String> names = new ArrayList<>();
        names.add("GoCity Bad Product");
        names.add("GoCity Good Product");
        names.add("GoCity Nice Product");
        names.add("GoCity Super Product");
        names.add("GoCity Super Product");
        names.add("GoCity Useless Product");
        return names;
    }

    private List<Product> addExtraProducts(LocalDateTime dateTime) {
        List<Product> products = new ArrayList<>();
        Category category = new Category(3, "test category", dateTime);
        Product prod1 = new Product("3", "GoCity Bad Product",
                "Gives super powers to the one drinks it", "3", dateTime, dateTime, dateTime, category);
        Product prod2 = new Product("3", "GoCity Useless Product",
                "Gives super powers to the one drinks it", "3", dateTime, dateTime, dateTime, category);
        Product prod3 = new Product("3", "GoCity Good Product",
                "Gives super powers to the one drinks it", "3", dateTime, dateTime, dateTime, category);
        Product prod4 = new Product("3", "GoCity Nice Product",
                "Gives super powers to the one drinks it", "3", dateTime, dateTime, dateTime, category);
        products.add(prod1);
        products.add(prod2);
        products.add(prod3);
        products.add(prod4);
        return products;
    }
}
