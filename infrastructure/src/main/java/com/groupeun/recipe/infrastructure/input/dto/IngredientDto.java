package com.groupeun.recipe.infrastructure.input.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class IngredientDto {

    private UUID id;
    private int quantity;
}
