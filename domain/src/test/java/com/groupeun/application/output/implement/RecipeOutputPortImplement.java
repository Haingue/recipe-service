package com.groupeun.application.output.implement;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.domain.exception.RecipeIdAlreadyUsed;
import com.groupeun.recipe.domain.exception.RecipeNotFound;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeOutputPortImplement implements RecipeOutputPort {

    private static RecipeOutputPortImplement instance = new RecipeOutputPortImplement();
    private static HashMap<UUID, Recipe> store;

    private RecipeOutputPortImplement() {
        super();
        this.store = new HashMap<>();
    }

    public static RecipeOutputPort getInstance () {
        return RecipeOutputPortImplement.instance;
    }
    public static HashMap<UUID, Recipe> getStore () {
        return RecipeOutputPortImplement.store;
    }

    @Override
    public Optional<Recipe> findOne(UUID id) {
        Recipe recipe = store.get(id);
        if (recipe == null) return Optional.empty();
        return Optional.of(recipe);
    }

    @Override
    public List<Recipe> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Recipe> findAllByNameRegex(String nameRegex) {
        return store.values().stream()
                .filter(recipe -> recipe.getName().matches(nameRegex))
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> search(String nameRegex, int preparationTime) {
        return store.values().stream()
                .filter(recipe -> recipe.getName().matches(nameRegex) || recipe.getPreparationTime() == preparationTime)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Recipe> create(UUID id, String name,String description, double nutritionalScore, int preparationTime, UUID authorId) {
        if (store.containsKey(id)) throw new RecipeIdAlreadyUsed(id);
        Recipe newRecipe = new Recipe();
        newRecipe.setId(id);
        newRecipe.setName(name);
        newRecipe.setDescription(description);
        newRecipe.setNutritionalScore(nutritionalScore);
        newRecipe.setPreparationTime(preparationTime);
        newRecipe.setAuthorId(authorId);
        store.put(id, newRecipe);
        return Optional.of(newRecipe);
    }

    @Override
    public Optional<Recipe> update(UUID id, String name,String description, double nutritionalScore, int preparationTime, UUID authorId) {
        Recipe existingRecipe = store.get(id);
        existingRecipe.setName(name);
        existingRecipe.setDescription(description);
        existingRecipe.setNutritionalScore(nutritionalScore);
        existingRecipe.setPreparationTime(preparationTime);
        existingRecipe.setAuthorId(authorId);
        store.put(id, existingRecipe);
        return Optional.of(existingRecipe);
    }

    @Override
    public void delete(UUID id) {
        if (!store.containsKey(id)) throw new RecipeNotFound(id);
        store.remove(id);
    }
}
