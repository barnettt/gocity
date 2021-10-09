package com.gocity.leisure.pass.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    Integer id;
    String name;
    String description;
    int categoryId;
    LocalDateTime creationDate;
    LocalDateTime updatedDate;
    LocalDateTime lastPurchasedDate;
    CategoryDto category;
}
