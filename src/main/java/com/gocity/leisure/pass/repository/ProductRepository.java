package com.gocity.leisure.pass.repository;

import java.util.List;

import com.gocity.leisure.pass.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAllByCategoryId(Integer categoryId);
    List<Product> findAll();
}
