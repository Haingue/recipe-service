package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.domain.exception.IngredientNotValid;
import com.groupeun.recipe.domain.exception.IngredientsNotExist;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Setting;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
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
        Set<Ingredient> allProductByRecipe = ingredientOutputPort.findAllProductByRecipe(recipeId);
        if (allProductByRecipe == null) return new HashSet<>();
        return allProductByRecipe;
    }

    public Set<Ingredient> updateIngredientList(UUID recipeId, Set<Ingredient> ingredients) {
        if (Setting.CHECK_INGREDIENT.getValue().equalsIgnoreCase("true")) {
            boolean allIngredientExist = productService.checkIngredientExist(
                    ingredients.stream().map(Ingredient::getId).collect(Collectors.toSet()));
            if (!allIngredientExist) throw new IngredientsNotExist();
        }

        Set<Ingredient> existingIngredient = this.findAllIngredientByRecipeId(recipeId);
        if (existingIngredient.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
                existingIngredient.remove(ingredient);
                ingredientOutputPort.save(recipeId, ingredient.getId(), ingredient.getQuantity());
            }
        }
        existingIngredient.forEach(oldIngredient -> ingredientOutputPort.delete(recipeId, oldIngredient.getId()));
        return ingredients;
    }

    public boolean ingredientIsValid (Ingredient ingredient) {
        if (ingredient.getId() == null) throw new IngredientNotValid("id", null);
        if (ingredient.getQuantity() < 1) throw new IngredientNotValid("quantity", ingredient.getQuantity());
        return true;
    }

    public boolean ingredientsAreValid (Set<Ingredient> ingredients) {
        Predicate<Ingredient> ingredientIsValid = this::ingredientIsValid;
        return ingredients.stream().noneMatch(ingredientIsValid.negate());
    }
}
