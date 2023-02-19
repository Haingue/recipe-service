package com.groupeun.recipe.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class RecipeStep {

    private UUID recipeId;
    private UUID ingredientId;
    private int stepNumber;
    private String description;

}
