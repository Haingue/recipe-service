package com.groupeun.recipe.application.ports.output;

import com.groupeun.recipe.domain.model.Ingredient;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface IngredientOutputPort {

    Set<Ingredient> findAllProductByRecipe (UUID recipeId);

    Ingredient save(UUID recipeId, UUID ingredientId, int quantity);

    void delete (UUID recipeId, UUID ingredientId);

}
