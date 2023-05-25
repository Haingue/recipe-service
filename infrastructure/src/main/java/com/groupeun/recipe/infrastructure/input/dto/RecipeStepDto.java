package com.groupeun.recipe.infrastructure.input.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RecipeStepDto {
    private int stepNumber;
    private String description;
}
