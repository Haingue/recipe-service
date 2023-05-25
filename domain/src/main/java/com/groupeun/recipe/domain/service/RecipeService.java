package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.input.RecipeInputPort;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.domain.exception.DomainException;
import com.groupeun.recipe.domain.exception.IngredientsNotExist;
import com.groupeun.recipe.domain.exception.RecipeNotFound;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.domain.model.Setting;
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

    @Override
    public Recipe findOne(UUID id) {
        return recipeOutputPort.findOne(id)
                .orElseThrow(() -> new RecipeNotFound(id));
    }

    @Override
    public List<Recipe> findAllByNameRegex(String nameRegex) {
        return recipeOutputPort.findAllByNameRegex(nameRegex);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeOutputPort.findAll();
    }

    @Override
    public List<Recipe> search(String nameRegex, int preparationTime) {
        return recipeOutputPort.search(nameRegex, preparationTime);
    }

    @Override
    public Recipe create(Recipe recipe) {
        return this.create(recipe.getName(), recipe.getDescription(), recipe.getNutritionalScore(),
                recipe.getPreparationTime(), recipe.getAuthorId(), recipe.getIngredients(), recipe.getSteps());
    }

    @Override
    public Recipe create(String name, String description, double nutritionalScore, int preparationTime, UUID authorId,
                         Set<Ingredient> ingredients, Set<RecipeStep> steps) {
        if (Setting.CHECK_INGREDIENT.getValue().equalsIgnoreCase("true")) {
            boolean allIngredientExist = ingredientService.checkIngredientExist(
                    ingredients.stream().map(Ingredient::getId).collect(Collectors.toSet()));
            if (!allIngredientExist) throw new IngredientsNotExist();
        }
        // TODO Check if there is a recipe with [name, author]
        return recipeOutputPort.create(UUID.randomUUID(), name, description, nutritionalScore, preparationTime,
                authorId, steps)
                .orElseThrow(() -> new DomainException(String.format("Error to create Recipe[name=%s]", name)));
    }

    @Override
    public Recipe update(Recipe recipe) {
        return this.update(recipe.getId(), recipe.getName(), recipe.getDescription(),
                recipe.getNutritionalScore(), recipe.getPreparationTime(),
                recipe.getAuthorId(), recipe.getIngredients(), recipe.getSteps());
    }

    @Override
    public Recipe update(UUID id, String name, String description, double nutritionalScore, int preparationTime, UUID authorId,
                         Set<Ingredient> ingredients, Set<RecipeStep> steps) {
        if (recipeOutputPort.findOne(id).isPresent()) throw new RecipeNotFound(id);
        if (Setting.CHECK_INGREDIENT.getValue().equalsIgnoreCase("true")) {
            boolean allIngredientExist = ingredientService.checkIngredientExist(
                    ingredients.stream().map(Ingredient::getId).collect(Collectors.toSet()));
            if (!allIngredientExist) throw new IngredientsNotExist();
        }
        return recipeOutputPort.update(id, name, description, nutritionalScore, preparationTime, authorId, steps)
                .orElseThrow(() -> new DomainException(String.format("Error to update Recipe[name=%s]", name)));
    }

    @Override
    public void delete(Recipe recipe) {
        this.delete(recipe.getId());
    }

    @Override
    public void delete(UUID id) {
        if (recipeOutputPort.findOne(id).isPresent()) throw new RecipeNotFound(id);
        recipeOutputPort.delete(id);
    }
}
