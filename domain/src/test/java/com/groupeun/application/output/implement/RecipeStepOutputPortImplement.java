package com.groupeun.application.output.implement;

import com.groupeun.recipe.application.ports.output.RecipeStepOutputPort;
import com.groupeun.recipe.domain.model.RecipeStep;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RecipeStepOutputPortImplement implements RecipeStepOutputPort {

    private static RecipeStepOutputPortImplement instance = new RecipeStepOutputPortImplement();
    private static HashMap<UUID, Set<RecipeStep>> store;

    private RecipeStepOutputPortImplement() {
        super();
        this.store = new HashMap<>();
    }

    public static RecipeStepOutputPort getInstance () {
        return RecipeStepOutputPortImplement.instance;
    }
    public static HashMap<UUID, Set<RecipeStep>> getStore () {
        return RecipeStepOutputPortImplement.store;
    }

    @Override
    public Set<RecipeStep> findAllRecipeStepsByRecipe(UUID recipeId) {
        if (!store.containsKey(recipeId)) store.put(recipeId, new HashSet<>());
        return store.get(recipeId);
    }

    @Override
    public RecipeStep save(UUID recipeId, int stepNumber, String description) {
        if (!store.containsKey(recipeId)) store.put(recipeId, new HashSet<>());

        RecipeStep step = generateRecipeStep(recipeId, stepNumber, description);
        if (store.get(recipeId).add(step)) return step;
        return null;
    }

    @Override
    public void delete(UUID recipeId, int stepNumber) {
        if (store.containsKey(recipeId)) store.put(recipeId, new HashSet<>());
            store.get(recipeId).remove(generateRecipeStep(recipeId, stepNumber, null));
    }

    private RecipeStep generateRecipeStep (UUID recipeId, int stepNumber, String description) {
        RecipeStep step = new RecipeStep();
        step.setStepNumber(stepNumber);
        step.setDescription(description);
        return step;
    }
}
