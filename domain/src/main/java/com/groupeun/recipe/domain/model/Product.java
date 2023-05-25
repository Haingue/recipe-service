package com.groupeun.recipe.domain.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Product {

    private UUID id;
    private String name;
    private String description;
    private double nutritionalScore;
    private double price;
    private String type;
    private String category;
    private Set<String> allergens = new HashSet<>();
}
