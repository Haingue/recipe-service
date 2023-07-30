package com.groupeun.application.output.implement;

import com.groupeun.recipe.application.ports.output.RecipeStepOutputPort;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecipeStepOutputPortImplement implements RecipeStepOutputPort {

    private static final RecipeStepOutputPortImplement instance = new RecipeStepOutputPortImplement();
    private static HashMap<UUID, Set<RecipeStep>> store;

    private RecipeStepOutputPortImplement() {
        super();
        store = new HashMap<>();
    }

    public static RecipeStepOutputPort getInstance () {
        return RecipeStepOutputPortImplement.instance;
    }
    public static HashMap<UUID, Set<RecipeStep>> getStore () {
        return RecipeStepOutputPortImplement.store;
    }

    public static Set<RecipeStep> createRecipeStepList (int number) {
        Random random = new Random();
        return Stream.iterate(0, i -> i + 1).limit(number)
                .map(v -> createRecipeStep(random.nextInt(), OutputUtils.generateString(5)))
                .collect(Collectors.toSet());
    }

    public static RecipeStep createRecipeStep (int stepNumber, String description) {
        RecipeStep recipeStep = new RecipeStep();
        recipeStep.setStepNumber(stepNumber);
        recipeStep.setDescription(description);
        return recipeStep;
    }

    @Override
    public Set<RecipeStep> findAllRecipeStepsByRecipe(UUID recipeId) {
        Set<RecipeStep> recipeSteps = store.get(recipeId);
        if (recipeSteps == null) return new HashSet<>();
        return recipeSteps;
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
