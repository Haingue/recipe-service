package com.groupeun.recipe.infrastructure.output.mapper;

import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.infrastructure.output.entity.RecipeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecipeOutputMapper {

    @Autowired
    private RecipeStepOutputMapper recipeStepOutputMapper;

    public RecipeEntity modelToEntity (Recipe model) {
        RecipeEntity entity = new RecipeEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setNutritionalScore(model.getNutritionalScore());
        entity.setPreparationTime(model.getPreparationTime());
        entity.setAuthorId(model.getAuthorId());
        entity.getSteps()
                .addAll(model.getSteps().stream().map(recipeStepOutputMapper::modelToEntity).collect(Collectors.toList()));
        return entity;
    }

    public Recipe entityToModel (RecipeEntity entity) {
        Recipe model = new Recipe();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setNutritionalScore(entity.getNutritionalScore());
        model.setPreparationTime(entity.getPreparationTime());
        model.setAuthorId(entity.getAuthorId());
        model.getSteps()
                .addAll(entity.getSteps().stream().map(recipeStepOutputMapper::entityToModel).collect(Collectors.toList()));
        return model;
    }
}
