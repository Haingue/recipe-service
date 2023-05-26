package com.groupeun.recipe.infrastructure.output.mapper;

import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.infrastructure.output.entity.RecipeEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RecipeOutputMapper {

    public static RecipeEntity modelToEntity (Recipe model) {
        RecipeEntity entity = new RecipeEntity();
        entity.setId(model.getId().toString());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setNutritionalScore(model.getNutritionalScore());
        entity.setPreparationTime(model.getPreparationTime());
        entity.setAuthorId(model.getAuthorId().toString());
        return entity;
    }

    public static Recipe entityToModel (RecipeEntity entity) {
        Recipe model = new Recipe();
        model.setId(UUID.fromString(entity.getId()));
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setNutritionalScore(entity.getNutritionalScore());
        model.setPreparationTime(entity.getPreparationTime());
        model.setAuthorId(UUID.fromString(entity.getAuthorId()));
        return model;
    }
}
