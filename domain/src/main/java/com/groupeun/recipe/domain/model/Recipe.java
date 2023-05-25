package com.groupeun.recipe.domain.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Recipe {

    private UUID id;
    private String name;
    private String description;
    private double nutritionalScore;
    private int preparationTime;
    private UUID authorId;
    private Set<Ingredient> ingredients = new HashSet<>();
    private Set<RecipeStep> steps = new HashSet<>();

    public boolean addIngredient (Ingredient ingredient) {
        return this.ingredients.add(ingredient);
    }
    public boolean addIngredients (Set<Ingredient> ingredients) {
        return this.ingredients.addAll(ingredients);
    }

    public boolean addStep (RecipeStep step) {
        return this.steps.add(step);
    }
    public boolean addSteps (Set<RecipeStep> steps) {
        return this.steps.addAll(steps);
    }

    public double getRecipeCost () {
        return ingredients.stream().map(ingredient -> ingredient.getQuantity() * ingredient.getDetails().getPrice())
                .reduce(Double::sum).orElse(0.);
    }
}
