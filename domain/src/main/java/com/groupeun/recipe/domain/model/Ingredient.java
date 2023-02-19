package com.groupeun.recipe.domain.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Ingredient {

    private UUID id;
    private String name;
    private String description;
    private double nutritionalScore;
    private String type;
    private String category;
    private Set<String> allergens = new HashSet<>();
}
