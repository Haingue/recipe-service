package com.groupeun.recipe.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Ingredient {

    private UUID id;
    private int quantity;
    private Product details;

}
