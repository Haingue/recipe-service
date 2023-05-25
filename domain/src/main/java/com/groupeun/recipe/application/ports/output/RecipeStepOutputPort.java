package com.groupeun.recipe.application.ports.output;

import com.groupeun.recipe.domain.model.RecipeStep;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RecipeStepOutputPort {

    Set<RecipeStep> findAllRecipeStepsByRecipe (UUID recipeId);

    RecipeStep save (UUID recipeId, int stepNumber, String description);

    void delete (UUID recipeId, int stepNumber);

}
