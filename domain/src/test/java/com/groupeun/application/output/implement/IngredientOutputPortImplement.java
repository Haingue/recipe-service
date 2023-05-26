package com.groupeun.application.output.implement;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IngredientOutputPortImplement implements IngredientOutputPort {

    private static IngredientOutputPortImplement instance = new IngredientOutputPortImplement();
    private static HashMap<UUID, Set<Ingredient>> store;

    private IngredientOutputPortImplement() {
        super();
        store = new HashMap<>();
    }

    public static IngredientOutputPort getInstance () {
        return IngredientOutputPortImplement.instance;
    }
    public static HashMap<UUID, Set<Ingredient>> getStore () {
        return IngredientOutputPortImplement.store;
    }

    @Override
    public Set<Ingredient> findAllProductByRecipe(UUID recipeId) {
        if (!store.containsKey(recipeId)) store.put(recipeId, new HashSet<>());
        return store.get(recipeId);
    }

    @Override
    public Ingredient save(UUID recipeId, UUID ingredientId, int quantity) {
        if (!store.containsKey(recipeId)) store.put(recipeId, new HashSet<>());
        Ingredient ingredient = generateIngredient(ingredientId, quantity, null);
        store.get(recipeId).remove(ingredient);
        return ingredient;
    }

    @Override
    public void delete(UUID recipeId, UUID ingredientId) {
        if (store.containsKey(recipeId))
            store.get(recipeId).remove(generateIngredient(ingredientId, 0, null));
    }

    private Ingredient generateIngredient (UUID ingredientId, int quantity, Product details) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setQuantity(quantity);
        ingredient.setDetails(details);
        return ingredient;
    }
}
