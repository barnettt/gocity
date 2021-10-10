package com.gocity.leisure.pass.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.gocity.leisure.pass.dto.ProductDto;
import com.gocity.leisure.pass.entities.Category;
import com.gocity.leisure.pass.entities.Product;
import com.gocity.leisure.pass.exception.GoCityCategoryNotFoundException;
import com.gocity.leisure.pass.exception.GoCityProductNotFoundException;
import com.gocity.leisure.pass.repository.CategoryRepository;
import com.gocity.leisure.pass.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoCityProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public GoCityProductService(ProductRepository productRepository,
                                CategoryRepository categoryRepository,
                                ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductDto> retrieveAllProduct() {
        List<Product> products = productRepository.findAll();
        checkProductsNotEmpty(products);
        return products.stream().map(this::getDTo).collect(Collectors.toList());
    }

    public List<ProductDto> sortProductsByName() {
        Comparator<Product> productComparator
                = Comparator.comparing(Product::getName);
        List<Product> products = productRepository.findAll();
        checkProductsNotEmpty(products);
        Collections.sort(products, productComparator);
        return products.stream().map(this::getDTo).collect(Collectors.toList());
    }


    public List<ProductDto> retrieveProductsByCategory(final int categoryId) {
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        checkProductsNotEmpty(products);
        return products.stream().map(this::getDTo).collect(Collectors.toList());
    }

    public void deleteProduct(final int id) {
        productRepository.deleteById(id);
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Product product = getProduct(productDto);
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        product.setCategory(category.orElseThrow(GoCityCategoryNotFoundException::new));
        product.setCategoryId(category.orElseThrow(GoCityCategoryNotFoundException::new).getId());
        Product savedProduct = productRepository.save(product);
        return getDTo(savedProduct);
    }

    public ProductDto addProduct(final ProductDto productDto) {
        Product product = getProduct(productDto);
        Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
        product.setCategory(category.orElseThrow(GoCityCategoryNotFoundException::new));
        product.setCategoryId(category.orElseThrow(GoCityCategoryNotFoundException::new).getId());
        Product savedProduct = productRepository.save(product);
        return getDTo(savedProduct);
    }

    private ProductDto getDTo(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private Product getProduct(ProductDto productDto) {
        String productId = productDto.getId() != null ? String.valueOf(productDto.getId()) : null;
        Category category = new Category(productDto.getCategoryId(), "", null);
        return new Product(productId,
                productDto.getName(), productDto.getDescription(),
                String.valueOf(productDto.getCategoryId()),
                productDto.getCreationDate(),
                LocalDateTime.now(), LocalDateTime.now(),
                category);

    }

    private void checkProductsNotEmpty(final List<Product> products) {
        if (products.isEmpty()) {
            throw new GoCityProductNotFoundException();
        }
    }
}
