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
public class CategoryDto {

    private Integer id;
    private String categoryName;
    private LocalDateTime creationDate;
}
