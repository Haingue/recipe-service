package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.domain.exception.IngredientsNotExist;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Setting;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class IngredientService {

    private ProductService productService;
    private IngredientOutputPort ingredientOutputPort;

    public Set<Ingredient> findAllIngredientByRecipeId (UUID recipeId) {
        return ingredientOutputPort.findAllProductByRecipe(recipeId);
    }

    public Set<Ingredient> updateIngredientList(UUID recipeId, Set<Ingredient> ingredients) {
        if (Setting.CHECK_INGREDIENT.getValue().equalsIgnoreCase("true")) {
            boolean allIngredientExist = productService.checkIngredientExist(
                    ingredients.stream().map(Ingredient::getId).collect(Collectors.toSet()));
            if (!allIngredientExist) throw new IngredientsNotExist();
        }

        Set<Ingredient> existingIngredient = this.findAllIngredientByRecipeId(recipeId);
        for (Ingredient ingredient : ingredients) {
            existingIngredient.remove(ingredient);
            ingredientOutputPort.save(recipeId, ingredient.getId(), ingredient.getQuantity());
        }
        existingIngredient.forEach(oldIngredient -> ingredientOutputPort.delete(recipeId, oldIngredient.getId()));
        return ingredients;
    }

    public boolean ingredientIsValid (Ingredient ingredient) {
        return ingredient.getId() != null && ingredient.getQuantity() > 0;
    }

    public boolean ingredientsAreValid (Set<Ingredient> ingredients) {
        Predicate<Ingredient> ingredientIsValid = this::ingredientIsValid;
        return !ingredients.stream().anyMatch(ingredientIsValid.negate());
    }
}
