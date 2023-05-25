package com.groupeun.recipe.infrastructure.output.mapper;

import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.infrastructure.output.entity.IngredientEntity;
import com.groupeun.recipe.infrastructure.output.entity.RecipeEntity;
import com.groupeun.recipe.infrastructure.output.entity.id.IngredientEntityId;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

public class IngredientOutputMapper {

    public static IngredientEntity modelToEntity (UUID recipeId, Ingredient model) {
        IngredientEntityId id = new IngredientEntityId();
        id.setIngredientId(model.getId());
        id.setRecipeId(recipeId);

        IngredientEntity entity = new IngredientEntity();
        entity.setId(id);
        entity.setQuantity(model.getQuantity());
        return entity;
    }

    public static Ingredient entityToModel (IngredientEntity entity) {
        Ingredient model = new Ingredient();
        model.setId(entity.getId().getIngredientId());
        model.setQuantity(entity.getQuantity());
        return model;
    }
}
