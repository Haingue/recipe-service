package com.groupeun.recipe.infrastructure.input.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class RecipeDto {
    private UUID id;
    private String name;
    private String description;
    private double nutritionalScore;
    private int preparationTime;
    private UUID authorId;
    private Set<RecipeStepDto> steps = new HashSet<>();
}
