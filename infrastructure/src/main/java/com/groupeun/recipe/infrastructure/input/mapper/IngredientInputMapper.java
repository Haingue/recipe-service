package com.groupeun.recipe.infrastructure.input.mapper;

import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.infrastructure.input.dto.IngredientDto;

public class IngredientInputMapper {

    public static IngredientDto modelToDto (Ingredient model) {
        IngredientDto dto = new IngredientDto();
        dto.setId(model.getId());
        dto.setQuantity(model.getQuantity());
        return dto;
    }

    public static Ingredient dtoToModel (IngredientDto dto) {
        Ingredient model = new Ingredient();
        model.setId(dto.getId());
        model.setQuantity(dto.getQuantity());
        return model;
    }

}
