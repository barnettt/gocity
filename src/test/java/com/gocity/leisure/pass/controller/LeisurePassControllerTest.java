package com.gocity.leisure.pass.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.leisure.pass.dto.CategoryDto;
import com.gocity.leisure.pass.dto.ProductDto;
import com.gocity.leisure.pass.entities.Product;
import com.gocity.leisure.pass.exception.GoCityProductNotFoundException;
import com.gocity.leisure.pass.service.GoCityProductService;
import com.gocity.leisure.pass.service.GoCityServiceTestParent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@ActiveProfiles({"test"})
@SpringBootTest()
@AutoConfigureMockMvc
class LeisurePassControllerTest extends GoCityServiceTestParent {

    private static final int CATEGORY_ID = 3;
    private static final int CATEGORY_NOTFOUND_ID = 2;
    private static final int EXPECTED_RETURN_COUNT = 2;
    private static final int EXPECTED_RETURN_PRODUCT_ID = 2;
    private static final String PRODUCTS_NOT_FOUND = "product/products not found";

    @Autowired
    MockMvc mvc;

    @MockBean
    GoCityProductService goCityProductService;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        LocalDateTime dateTime = LocalDateTime.now();
        when(goCityProductService.retrieveAllProduct()).thenReturn(getProductDtoList(dateTime));
        when(goCityProductService.retrieveProductsByCategory(3)).thenReturn(getProductDtoList(dateTime));
        when(goCityProductService.updateProduct(any())).thenReturn(getGoCityProduct());
        when(goCityProductService.sortProductsByName()).thenReturn(getProductDtoList(dateTime));

        ProductDto productDto = getGoCityProduct();
        productDto.setId(null);
        productDto.setName("New product");
        productDto.setDescription("Testing add new product");
        when(goCityProductService.addProduct(any())).thenReturn(productDto);
    }

    @DisplayName("I want to see all products")
    @Test
    void shouldReturnAllProducts() throws Exception {

        String result = mvc.perform(get("/gocity/v1.0/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        TypeReference<ArrayList<Product>> typeRef = new TypeReference<>() {
        };
        List<Product> products = mapper.readValue(result, typeRef);
        assertThat(products.size(), is(EXPECTED_RETURN_COUNT));
    }

    @DisplayName("I want to sort products")
    @Test
    void shouldReturnSortedProducts() throws Exception {

        String result = mvc.perform(get("/gocity/v1.0/products/sort")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        TypeReference<ArrayList<Product>> typeRef = new TypeReference<>() {
        };
        List<Product> products = mapper.readValue(result, typeRef);
        assertThat(products.size(), is(EXPECTED_RETURN_COUNT));
    }


    @DisplayName("I want to see products by category")
    @Test
    void shouldReturnProductsByCategory() throws Exception {
        String result = mvc.perform(get("/gocity/v1.0/products/category/{categoryId}", CATEGORY_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        TypeReference<ArrayList<Product>> typeRef = new TypeReference<>() {
        };
        List<Product> products = mapper.readValue(result, typeRef);
        assertThat(products.size(), is(EXPECTED_RETURN_COUNT));
    }

    @DisplayName("Not Found product by category   ")
    @Test
    void shouldReturnNotFoundProductsByCategory() throws Exception {
        when(goCityProductService.retrieveProductsByCategory(2)).thenThrow(new GoCityProductNotFoundException());
        mvc.perform(get("/gocity/v1.0/products/category/{categoryId}", CATEGORY_NOTFOUND_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(PRODUCTS_NOT_FOUND, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @DisplayName("I want to be able to update a product")
    @Test
    void shouldUpdateProduct() throws Exception {
        String json = mapper.writeValueAsString(getGoCityProduct());
        mvc.perform(post("/gocity/v1.0/products/{id}", EXPECTED_RETURN_PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(25)))
                .andExpect(jsonPath("$.category.id", is(3)))
                .andExpect(jsonPath("$.name", is("GoCity Super Product")));
    }

    @DisplayName("I want to be able to delete a product")
    @Test
    void shouldDeleteProduct() throws Exception {
        mvc.perform(delete("/gocity/v1.0/products/{id}", EXPECTED_RETURN_PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @DisplayName("I want to be able to Add a new product")
    @Test
    void shouldAddNewProduct() throws Exception {
        ProductDto productDto = getGoCityProduct();
        productDto.setId(null);
        productDto.setName("New product");
        productDto.setDescription("Testing add new product");
        String json = mapper.writeValueAsString(productDto);
        mvc.perform(put("/gocity/v1.0/products", EXPECTED_RETURN_PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.description", is("Testing add new product")))
                .andExpect(jsonPath("$.name", is("New product")));
    }

    @DisplayName("I want to be able to update a product")
    @Test
    void shouldPurchaseDateError() throws Exception {
        ProductDto dto = getGoCityProduct();
        dto.setLastPurchasedDate(LocalDateTime.now().minus(1, ChronoUnit.YEARS));
        String json = mapper.writeValueAsString(dto);
        mvc.perform(post("/gocity/v1.0/products/{id}", EXPECTED_RETURN_PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Last purchase date cannot be before creation date.",
                        result.getResolvedException() != null ? result.getResolvedException().getMessage() : "Unknown error returned"));

    }

    private ProductDto getGoCityProduct() {
        LocalDateTime dateTime = LocalDateTime.now();
        return ProductDto.builder()
                .id(25)
                .categoryId(3)
                .creationDate(dateTime)
                .name("GoCity Super Product")
                .description("Gives super powers to the one drinks it")
                .lastPurchasedDate(dateTime)
                .updatedDate(dateTime)
                .category(CategoryDto.builder().id(3)
                        .categoryName("test category")
                        .creationDate(dateTime)
                        .build())
                .build();
    }
}
