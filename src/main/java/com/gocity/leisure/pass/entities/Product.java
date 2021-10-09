package com.gocity.leisure.pass.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    String description;
    @Column(name = "CATEGORY_ID")
    int categoryId;
    @Column(name = "CREATION_DATE")
    LocalDateTime creationDate;
    @Column(name = "UPDATED_DATE")
    LocalDateTime updatedDate;
    @Column(name = "LAST_PURCHASED_DATE")
    LocalDateTime lastPurchasedDate;

    public Product(final String productId, final String name, String description, final String categoryId,  final LocalDateTime  creationDate, final LocalDateTime  updatedDate, final LocalDateTime  lastPurchasedDate) {
        this.id = Integer.valueOf(productId);
        this.name = name;
        this.description = description;
        this.categoryId = Integer.valueOf(categoryId);
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.lastPurchasedDate = lastPurchasedDate;
    }

}
