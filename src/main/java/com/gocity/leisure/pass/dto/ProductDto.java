package com.gocity.leisure.pass.dto;

import java.time.LocalDateTime;

import com.gocity.leisure.pass.exception.GoCityLastPurchaseDateException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private int categoryId;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
    private LocalDateTime lastPurchasedDate;
    private CategoryDto category;

    public static void validate(ProductDto dto) throws GoCityLastPurchaseDateException {
       if (dto.getLastPurchasedDate().isBefore(dto.getCreationDate())) {
           throw new GoCityLastPurchaseDateException();
       }
    }
}
