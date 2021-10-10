package com.gocity.leisure.pass.controller;

import java.util.List;

import com.gocity.leisure.pass.dto.ProductDto;
import com.gocity.leisure.pass.service.GoCityProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gocity/v1.0")
public class LeisurePassController {

    private GoCityProductService goCityProductService;

    @Autowired
    public LeisurePassController(GoCityProductService goCityProductService) {
        this.goCityProductService = goCityProductService;
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductDto> getAllProducts() {
        return goCityProductService.retrieveAllProduct();
    }

    @GetMapping(value = "/products/category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductDto> getByCategoryProducts(@PathVariable int categoryId) {
        return goCityProductService.retrieveProductsByCategory(categoryId);
    }

    @PostMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDto updateProduct(@PathVariable int id, @RequestBody ProductDto product) {
        ProductDto.validate(product);
        product.setId(id);
        return goCityProductService.updateProduct(product);
    }

    @DeleteMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void deleteProduct(@PathVariable int id) {
        goCityProductService.deleteProduct(id);
    }

    @PutMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return goCityProductService.addProduct(productDto);
    }

    @GetMapping(value = "/products/sort", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductDto> sortProducts() {
        return goCityProductService.sortProductsByName();
    }
}
