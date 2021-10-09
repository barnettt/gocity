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
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(name = "CATEGORY_NAME")
    String categoryName;
    @Column(name = "CREATION_DATE")
    LocalDateTime creationDate;

    public Category(Integer id, String categoryName, LocalDateTime creationDate) {
        this.id = id;
        this.categoryName = categoryName;
        this.creationDate = creationDate;

    }
}
