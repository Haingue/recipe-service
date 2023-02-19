package com.groupeun.recipe.domain.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Recipe {

    private UUID id;
    private String name;
    private String description;
    private double nutritionalScore;
    private int preparationTime;
    private UUID authorId;
    private Set<RecipeStep> steps = new HashSet<>();

}
