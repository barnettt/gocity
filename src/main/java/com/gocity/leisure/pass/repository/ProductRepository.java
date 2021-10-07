package com.gocity.leisure.pass.repository;

import com.gocity.leisure.pass.db.entities.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

}
