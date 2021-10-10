package com.gocity.leisure.pass.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @NotNull
    @Max(40)
    String name;
    @NotNull
    @Max(128)
    String description;
    @Column(name = "category_id" , updatable = false)
    int categoryId;
    @Column(name = "CREATION_DATE")
    LocalDateTime creationDate;
    @Column(name = "UPDATE_DATE")
    LocalDateTime updatedDate;
    @Column(name = "LAST_PURCHASED_DATE")
    LocalDateTime lastPurchasedDate;
    @JoinColumn( referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    Category category;

    public Product(final String productId, final String name, String description, final String categoryId,
                   final LocalDateTime creationDate, final LocalDateTime updatedDate,
                   final LocalDateTime lastPurchasedDate, Category category) {
        this.id = productId != null ? Integer.parseInt(productId) : null;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId != null ? Integer.parseInt(categoryId) : null;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.lastPurchasedDate = lastPurchasedDate;
        this.category = category;
    }

}
