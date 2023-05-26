package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.output.RecipeStepOutputPort;
import com.groupeun.recipe.domain.model.RecipeStep;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RecipeStepService {

    private RecipeStepOutputPort recipeStepOutputPort;

    public Set<RecipeStep> findAllRecipeStepsByRecipe (UUID recipeId) {
        return recipeStepOutputPort.findAllRecipeStepsByRecipe(recipeId);
    }

    public Set<RecipeStep> updateStepList (UUID recipeId, Set<RecipeStep> steps) {
        Set<RecipeStep> existingSteps = this.findAllRecipeStepsByRecipe(recipeId);
        for (RecipeStep step : steps) {
            existingSteps.remove(step);
            recipeStepOutputPort.save(recipeId, step.getStepNumber(), step.getDescription());
        }
        existingSteps.forEach(oldIngredient -> recipeStepOutputPort.delete(recipeId, oldIngredient.getStepNumber()));
        return steps;
    }

}
