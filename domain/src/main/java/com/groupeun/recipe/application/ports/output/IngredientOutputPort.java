package com.groupeun.recipe.application.ports.output;

import com.groupeun.recipe.domain.model.Ingredient;

import java.util.List;
import java.util.UUID;

public interface IngredientOutputPort {
    List<Ingredient> getIngredientDetails (List<UUID> ingredientIds);
}
