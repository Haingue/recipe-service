package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class IngredientService {

    IngredientOutputPort ingredientOutputPort;

    public boolean checkIngredientExist (Set<RecipeStep> steps) {
        if (steps.isEmpty()) return true;
        List<UUID> ingredientIds = steps.stream().map(RecipeStep::getIngredientId).collect(Collectors.toList());
        List<Ingredient> ingredients = ingredientOutputPort.getIngredientDetails(ingredientIds);
        return ingredients.size() == steps.size();
    }

    public boolean getIngredientDetails (Set<RecipeStep> steps) {
        if (steps.isEmpty()) return true;
        List<UUID> ingredientIds = steps.stream().map(RecipeStep::getIngredientId).collect(Collectors.toList());
        List<Ingredient> ingredients = ingredientOutputPort.getIngredientDetails(ingredientIds);
        return ingredients.size() == steps.size();
    }
}
