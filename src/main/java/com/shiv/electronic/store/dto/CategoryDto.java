package com.shiv.electronic.store.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private String categoryId;

    @NotBlank
    private String title;

//    @Max(3000)
    private String description;

    private String coverImageName;
}
