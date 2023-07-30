package com.groupeun.domain;

import com.groupeun.application.output.implement.*;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.domain.exception.RecipeNotFound;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.service.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RecipeServiceTest {

    private RecipeService recipeService = OutputUtils.recipeService;
    private RecipeOutputPortImplement recipeOutputPortImplement = RecipeOutputPortImplement.getInstance();

    @BeforeAll
    public static void prepareTest () {
        OutputUtils.initializeOutputPortImplement();
    }

    @Test
    public void testInsert () {
        UUID authorId = UUID.randomUUID();
        Assertions.assertDoesNotThrow(() -> {
            recipeService.create("Test", "Description test", 5D,10, authorId,
                    IngredientOutputPortImplement.createIngredientList(3), RecipeStepOutputPortImplement.createRecipeStepList(2));
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
        UUID firstId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Recipe createdRecipe = RecipeOutputPortImplement.createRecipe(firstId, "Test", "Description test", 5,
                10D, authorId, RecipeStepOutputPortImplement.createRecipeStepList(2), IngredientOutputPortImplement.createIngredientList(1));
        RecipeOutputPortImplement.getStore().put(firstId, createdRecipe);

        Assertions.assertDoesNotThrow(() -> {
            Recipe updatedRecipe = RecipeOutputPortImplement.createRecipe(createdRecipe.getId(), "Test 2", null, 2,
                    15D, authorId, RecipeStepOutputPortImplement.createRecipeStepList(3), IngredientOutputPortImplement.createIngredientList(2));

            recipeService.update(updatedRecipe.getId(), updatedRecipe.getName(), updatedRecipe.getDescription(), updatedRecipe.getNutritionalScore(),
                    updatedRecipe.getPreparationTime(), updatedRecipe.getAuthorId(), updatedRecipe.getIngredients(), updatedRecipe.getSteps());
            Recipe firstRecipe = recipeService.findOne(createdRecipe.getId());
            Assertions.assertNotNull(firstRecipe);
            Assertions.assertEquals(firstRecipe.getName(), updatedRecipe.getName());
            Assertions.assertEquals(firstRecipe.getDescription(), updatedRecipe.getDescription());
            Assertions.assertEquals(firstRecipe.getNutritionalScore(), updatedRecipe.getNutritionalScore());
            Assertions.assertEquals(firstRecipe.getPreparationTime(), updatedRecipe.getPreparationTime());
            Assertions.assertEquals(firstRecipe.getAuthorId(), updatedRecipe.getAuthorId());
        }, "Error to update recipe");
    }

    @Test
    public void testDelete () {
        UUID firstId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        RecipeOutputPortImplement.getStore()
                .put(
                        firstId,
                        RecipeOutputPortImplement.createRecipe(firstId, "Test", "Description test",
                                5,10D, authorId,
                                RecipeStepOutputPortImplement.createRecipeStepList(2),
                                IngredientOutputPortImplement.createIngredientList(1))
                );

        Assertions.assertDoesNotThrow(() -> {
            recipeService.delete(firstId);
        }, "Error to delete recipe by id");
        Assertions.assertThrows(RecipeNotFound.class, () -> {
            Recipe firstRecipe = recipeService.findOne(firstId);
            Assertions.assertNull(firstRecipe);
        }, "Error the deleted recipe is found");
    }
}
