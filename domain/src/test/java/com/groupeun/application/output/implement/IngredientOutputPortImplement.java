package com.groupeun.application.output.implement;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.application.ports.output.RecipeStepOutputPort;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Product;
import com.groupeun.recipe.domain.model.Recipe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IngredientOutputPortImplement implements IngredientOutputPort {

    private static final IngredientOutputPortImplement instance = new IngredientOutputPortImplement();
    private static HashMap<UUID, Set<Ingredient>> store;

    private IngredientOutputPortImplement() {
        super();
        store = new HashMap<>();
    }

    public static IngredientOutputPortImplement getInstance () {
        return IngredientOutputPortImplement.instance;
    }
    public static HashMap<UUID, Set<Ingredient>> getStore () {
        return IngredientOutputPortImplement.store;
    }


    public static Set<Ingredient> createIngredientList (int number) {
        Random random = new Random();
        return Stream.iterate(0, i -> i + 1).limit(number)
                .map(v -> createIngredient(UUID.randomUUID(), random.nextInt(10) + 1, createDetails(UUID.randomUUID(), OutputUtils.generateString(5))))
                .collect(Collectors.toSet());
    }

    public static Ingredient createIngredient (UUID id, int quantity, Product details) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setQuantity(quantity);
        ingredient.setDetails(details);
        return ingredient;
    }

    public static Product createDetails (UUID id, String name) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        return product;
    }

    @Override
    public Set<Ingredient> findAllProductByRecipe(UUID recipeId) {
        Set<Ingredient> ingredients = store.get(recipeId);
        if (ingredients != null) return ingredients;
        return new HashSet<>();
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
