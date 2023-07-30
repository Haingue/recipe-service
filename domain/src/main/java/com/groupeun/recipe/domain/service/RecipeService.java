package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.input.RecipeInputPort;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.domain.exception.DomainException;
import com.groupeun.recipe.domain.exception.IngredientNotValid;
import com.groupeun.recipe.domain.exception.RecipeNotFound;
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
public class RecipeService implements RecipeInputPort {

    private RecipeOutputPort recipeOutputPort;
    private IngredientService ingredientService;
    private RecipeStepService recipeStepService;

    @Override
    public Recipe findOne(UUID id) {
        return recipeOutputPort.findOne(id)
                .map(this::loadRecipeDetails)
                .orElseThrow(() -> new RecipeNotFound(id));
    }

    @Override
    public List<Recipe> findAllByNameRegex(String nameRegex) {
        return recipeOutputPort.findAllByNameRegex(nameRegex)
                .stream().map(this::loadRecipeDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findAll() {
        return recipeOutputPort.findAll()
                .stream().map(this::loadRecipeDetails)
                .collect(Collectors.toList());
    }

    private Recipe loadRecipeDetails (Recipe recipe) {
        recipe.addIngredients(ingredientService.findAllIngredientByRecipeId(recipe.getId()));
        recipe.addSteps(recipeStepService.findAllRecipeStepsByRecipe(recipe.getId()));
        return recipe;
    }

    @Override
    public List<Recipe> search(String nameRegex, int preparationTime) {
        return recipeOutputPort.search(nameRegex, preparationTime)
                .stream().map(this::loadRecipeDetails)
                .collect(Collectors.toList());
    }

    @Override
    public Recipe create(Recipe recipe) {
        return this.create(recipe.getName(), recipe.getDescription(), recipe.getNutritionalScore(),
                recipe.getPreparationTime(), recipe.getAuthorId(), recipe.getIngredients(), recipe.getSteps());
    }

    @Override
    public Recipe create(String name, String description, double nutritionalScore, int preparationTime, UUID authorId,
                         Set<Ingredient> ingredients, Set<RecipeStep> steps) {
        // TODO Check if there is a recipe with [name, author]
        if (!ingredientService.ingredientsAreValid(ingredients)) {
            throw new IngredientNotValid();
        }

        UUID recipeId = UUID.randomUUID();
        Recipe recipe = recipeOutputPort.create(recipeId, name, description, nutritionalScore, preparationTime, authorId)
                .orElseThrow(() -> new DomainException(String.format("Error to create Recipe[name=%s]", name)));
        ingredientService.updateIngredientList(recipeId, ingredients);
        recipeStepService.updateStepList(recipeId, steps);
        return recipe;
    }

    @Override
    public Recipe update(Recipe recipe) {
        return this.update(recipe.getId(), recipe.getName(), recipe.getDescription(),
                recipe.getNutritionalScore(), recipe.getPreparationTime(),
                recipe.getAuthorId(), recipe.getIngredients(), recipe.getSteps());
    }

    @Override
    public Recipe update(UUID recipeId, String name, String description, double nutritionalScore, int preparationTime, UUID authorId,
                         Set<Ingredient> ingredients, Set<RecipeStep> steps) {
        if (!recipeOutputPort.findOne(recipeId).isPresent()) throw new RecipeNotFound(recipeId);
        Recipe recipe = recipeOutputPort.update(recipeId, name, description, nutritionalScore, preparationTime, authorId)
                .orElseThrow(() -> new DomainException(String.format("Error to update Recipe[name=%s]", name)));
        ingredientService.updateIngredientList(recipeId, ingredients);
        recipeStepService.updateStepList(recipeId, steps);
        return recipe;
    }

    @Override
    public void delete(Recipe recipe) {
        this.delete(recipe.getId());
    }

    @Override
    public void delete(UUID id) {
        if (recipeOutputPort.findOne(id).isEmpty()) throw new RecipeNotFound(id);
        recipeOutputPort.delete(id);
    }
}
