package com.groupeun.domain;

import com.groupeun.application.output.implement.RecipeOutputPortImplement;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.domain.exception.RecipeIdAlreadyUsed;
import com.groupeun.recipe.domain.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public class RecipeServiceTest {

    RecipeOutputPort recipeOutputPort = RecipeOutputPortImplement.getInstance();

    @BeforeEach
    public void prepareTest () {
        Assertions.assertDoesNotThrow(() -> {
            recipeOutputPort.findAll()
                    .forEach(recipe -> recipeOutputPort.delete(recipe.getId()));
        });
    }

    @Test
    public void testInsert () {
        UUID firstId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Assertions.assertDoesNotThrow(() -> {
            recipeOutputPort.create(firstId, "Test", "Description test", 5D,
                    10, authorId, new HashSet<>());
        }, "Error to create new Recipe");
        Assertions.assertDoesNotThrow(() -> {
            Optional<Recipe> firstRecipe = recipeOutputPort.findOne(firstId);
            Assertions.assertTrue(firstRecipe.isPresent());
            Assertions.assertEquals(firstRecipe.get().getName(), "Test");
            Assertions.assertEquals(firstRecipe.get().getDescription(), "Description test");
            Assertions.assertEquals(firstRecipe.get().getNutritionalScore(), 5D);
            Assertions.assertEquals(firstRecipe.get().getPreparationTime(), 10);
            Assertions.assertEquals(firstRecipe.get().getAuthorId(), authorId);
        }, "Error to find recipe by id");
        Assertions.assertThrows(RecipeIdAlreadyUsed.class, () -> {
            recipeOutputPort.create(firstId, "Test", "Description test", 5D,
                    10, authorId, new HashSet<>());
        }, "Error to create duplicated Recipe, RecipeIdAlreadyUsed expected");
    }

    @Test
    public void testUpdate () {
        UUID firstId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Assertions.assertDoesNotThrow(() -> {
            recipeOutputPort.create(firstId, "Test 1", "Description test", 5D,
                    10, authorId, new HashSet<>());
        }, "Error to create new Recipe");
        Assertions.assertDoesNotThrow(() -> {
            recipeOutputPort.update(firstId, "Test 2", null, 2D,
                    5, authorId, new HashSet<>());
            Optional<Recipe> firstRecipe = recipeOutputPort.findOne(firstId);
            Assertions.assertTrue(firstRecipe.isPresent());
            Assertions.assertEquals(firstRecipe.get().getName(), "Test 2");
            Assertions.assertNull(firstRecipe.get().getDescription());
            Assertions.assertEquals(firstRecipe.get().getNutritionalScore(), 2D);
            Assertions.assertEquals(firstRecipe.get().getPreparationTime(), 5);
            Assertions.assertEquals(firstRecipe.get().getAuthorId(), authorId);
        }, "Error to update recipe");
    }

    @Test
    public void testDelete () {
        UUID firstId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Assertions.assertDoesNotThrow(() -> {
            recipeOutputPort.create(firstId, "Test", "Description test", 5D,
                    10, authorId, new HashSet<>());
        }, "Error to create new Recipe");
        Assertions.assertDoesNotThrow(() -> {
            recipeOutputPort.delete(firstId);
            Optional<Recipe> firstRecipe = recipeOutputPort.findOne(firstId);
            Assertions.assertFalse(firstRecipe.isPresent());
        }, "Error to delete recipe by id");
    }
}
