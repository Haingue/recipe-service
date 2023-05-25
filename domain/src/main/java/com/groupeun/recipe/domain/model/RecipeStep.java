package com.groupeun.recipe.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class RecipeStep {

    private int stepNumber;
    private String description;

}
