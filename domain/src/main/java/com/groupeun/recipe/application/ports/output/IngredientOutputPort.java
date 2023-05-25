package com.groupeun.recipe.application.ports.output;

import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.IngredientDetails;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IngredientOutputPort {
    Set<IngredientDetails> getIngredientDetails (Set<UUID> ingredientIds);
}
