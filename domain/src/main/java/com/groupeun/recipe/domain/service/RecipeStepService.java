package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.input.RecipeInputPort;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.application.ports.output.RecipeStepOutputPort;
import com.groupeun.recipe.domain.exception.DomainException;
import com.groupeun.recipe.domain.exception.IngredientNotValid;
import com.groupeun.recipe.domain.exception.IngredientsNotExist;
import com.groupeun.recipe.domain.exception.RecipeNotFound;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.domain.model.Setting;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
