package com.gocity.leisure.pass.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.gocity.leisure.pass.dto.ProductDto;
import com.gocity.leisure.pass.entities.Category;
import com.gocity.leisure.pass.entities.Product;
import com.gocity.leisure.pass.exception.GoCityCategoryNotFoundException;
import com.gocity.leisure.pass.repository.CategoryRepository;
import com.gocity.leisure.pass.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
class LeisurePassServiceTest extends GoCityServiceTestParent {

    @Mock
    ProductRepository repository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ModelMapper mapper;

    GoCityProductService service;

    @BeforeEach
    public void setUp() {
        service = new GoCityProductService(repository, categoryRepository, mapper);
    }

    @DisplayName("Return all products")
    @Test
    void shouldReturnAllProducts() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Product> products = getProductList(dateTime);
        List<ProductDto> expected = getProductDtoList(dateTime);
        when(repository.findAll()).thenReturn(products);
        when(mapper.map(any(), any())).thenReturn(expected.get(0));
        List<ProductDto> returnedProduct = service.retrieveAllProduct();
        assertIterableEquals(returnedProduct, expected);
    }

    @DisplayName("Return products by category")
    @Test
    void shouldReturnProductsByCategory() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Product> products = getProductList(dateTime);
        List<ProductDto> expected = getProductDtoList(dateTime);
        when(repository.findAllByCategoryId(anyInt())).thenReturn(products);
        when(mapper.map(any(), any())).thenReturn(expected.get(0));
        List<ProductDto> returnedProduct = service.retrieveProductsByCategory(3);
        assertIterableEquals(expected, returnedProduct);
    }

    @DisplayName("Update products")
    @Test
    void shouldUpdateProduct() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Product> products = getProductList(dateTime);
        List<ProductDto> expectedList = getProductDtoList(dateTime);
        ProductDto expected = expectedList.get(0);

        Product product = products.get(0);
        product.setName("test update product");
        expected.setName(product.getName());
        when(categoryRepository.findById(anyInt())).thenReturn(
                Optional.of(new Category(5, "test category", dateTime)));
        when(repository.save(any())).thenReturn(product);
        when(mapper.map(any(), any())).thenReturn(expected);
        ProductDto returnedProduct = service.updateProduct(expected);
        assertEquals(expected, returnedProduct);
    }

    @DisplayName("Throw category not found error")
    @Test
    void shouldThrowCategoryNotFoundException() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Product> products = getProductList(dateTime);
        List<ProductDto> expectedList = getProductDtoList(dateTime);
        ProductDto expected = expectedList.get(0);

        Product product = products.get(0);
        product.setName("test update product");
        expected.setName(product.getName());
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());
        GoCityCategoryNotFoundException ex = assertThrows(
                GoCityCategoryNotFoundException.class,
                () -> service.updateProduct(expected),
                "Category not found was not thrown"
        );
        assertEquals("Category not found", ex.getMessage());
    }

    @DisplayName("Should Delete product")
    @Test
    void shouldDeleteProduct() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Product> products = getProductList(dateTime);
        repository.delete(products.get(0));
        verify(repository, times(1)).delete(products.get(0));
    }

    @DisplayName("Add new products")
    @Test
    void shouldAddProduct() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Product> products = getProductList(dateTime);
        List<ProductDto> expectedList = getProductDtoList(dateTime);
        ProductDto expected = expectedList.get(0);
        Product product = products.get(0);
        product.setName("test add new product");
        expected.setName(product.getName());
        when(repository.save(any())).thenReturn(product);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(product.getCategory()));
        when(mapper.map(any(), any())).thenReturn(expected);
        ProductDto returnedProduct = service.addProduct(expected);
        assertEquals(expected, returnedProduct);
        verify(repository, times(1)).save(any());
    }


}
