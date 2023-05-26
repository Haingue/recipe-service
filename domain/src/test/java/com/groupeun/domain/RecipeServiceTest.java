package com.groupeun.domain;

import com.groupeun.application.output.implement.IngredientOutputPortImplement;
import com.groupeun.application.output.implement.OutputUtils;
import com.groupeun.application.output.implement.RecipeOutputPortImplement;
import com.groupeun.application.output.implement.RecipeStepOutputPortImplement;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.service.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RecipeServiceTest {

    private RecipeService recipeService = OutputUtils.recipeService;

    @BeforeAll
    public static void prepareTest () {
        OutputUtils.initializeOutputPortImplement();
    }

    @Test
    public void testInsert () {
        UUID authorId = UUID.randomUUID();
        Assertions.assertDoesNotThrow(() -> {
            recipeService.create("Test", "Description test", 5D,
                    10, authorId, OutputUtils.createIngredientList(3), OutputUtils.createRecipeStepList(2));
        }, "Error to create new Recipe");
        Recipe createdRecipe = RecipeOutputPortImplement.getStore()
                .values().stream()
                .filter(recipe -> recipe.getAuthorId().equals(authorId))
                .findFirst().get();

        Assertions.assertDoesNotThrow(() -> {
            Recipe firstRecipe = recipeService.findOne(createdRecipe.getId());
            Assertions.assertNotNull(firstRecipe);
            Assertions.assertEquals(firstRecipe.getName(), "Test");
            Assertions.assertEquals(firstRecipe.getDescription(), "Description test");
            Assertions.assertEquals(firstRecipe.getNutritionalScore(), 5D);
            Assertions.assertEquals(firstRecipe.getPreparationTime(), 10);
            Assertions.assertEquals(firstRecipe.getAuthorId(), authorId);

            Assertions.assertTrue(firstRecipe.getSteps().stream()
                    .allMatch(step -> RecipeStepOutputPortImplement.getStore().values().stream()
                            .anyMatch(steps -> steps.contains(step))));
            Assertions.assertTrue(firstRecipe.getIngredients().stream()
                    .allMatch(step -> IngredientOutputPortImplement.getStore().values().stream()
                            .anyMatch(ingredients -> ingredients.contains(step))));
        }, "Error to find recipe by id");
    }

    @Test
    public void testUpdate () {
        UUID authorId = UUID.randomUUID();
        Assertions.assertDoesNotThrow(() -> {
            recipeService.create("Test 1", "Description test", 5D,
                    10, authorId, OutputUtils.createIngredientList(3), OutputUtils.createRecipeStepList(2));
        }, "Error to create new Recipe");
        Recipe createdRecipe = RecipeOutputPortImplement.getStore()
                .values().stream()
                .filter(recipe -> recipe.getAuthorId().equals(authorId))
                .findFirst().get();

        Assertions.assertDoesNotThrow(() -> {
            recipeService.update(createdRecipe.getId(), "Test 2", null, 2D,
                    5, authorId, OutputUtils.createIngredientList(2), OutputUtils.createRecipeStepList(3));
            Recipe firstRecipe = recipeService.findOne(createdRecipe.getId());
            Assertions.assertNull(firstRecipe);
            Assertions.assertEquals(firstRecipe.getName(), "Test 2");
            Assertions.assertNull(firstRecipe.getDescription());
            Assertions.assertEquals(firstRecipe.getNutritionalScore(), 2D);
            Assertions.assertEquals(firstRecipe.getPreparationTime(), 5);
            Assertions.assertEquals(firstRecipe.getAuthorId(), authorId);
        }, "Error to update recipe");
    }

    @Test
    public void testDelete () {
        UUID firstId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Assertions.assertDoesNotThrow(() -> {
            recipeService.create("Test", "Description test", 5D,
                    10, authorId, OutputUtils.createIngredientList(1), OutputUtils.createRecipeStepList(2));
        }, "Error to create new Recipe");
        Assertions.assertDoesNotThrow(() -> {
            recipeService.delete(firstId);
            Recipe firstRecipe = recipeService.findOne(firstId);
            Assertions.assertNotNull(firstRecipe);
        }, "Error to delete recipe by id");
    }
}
