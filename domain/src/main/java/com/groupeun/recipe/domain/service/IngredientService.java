package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.IngredientDetails;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class IngredientService {

    IngredientOutputPort ingredientOutputPort;

    public boolean checkIngredientExist (Set<UUID> ingredientIdList) {
        if (ingredientIdList.isEmpty()) return true;
        Set<IngredientDetails> ingredients = ingredientOutputPort.getIngredientDetails(ingredientIdList);
        return ingredientIdList.size() == ingredients.size();
    }

    public Set<IngredientDetails> loadIngredientDetails (Set<UUID> ingredientIdList) {
        if (ingredientIdList.isEmpty()) return Collections.emptySet();
        Set<IngredientDetails> ingredients = ingredientOutputPort.getIngredientDetails(ingredientIdList);
        return ingredients;
    }
}
