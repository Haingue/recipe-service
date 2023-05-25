package com.groupeun.recipe.infrastructure.output.mapper;

import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.infrastructure.output.entity.RecipeStepEntity;
import com.groupeun.recipe.infrastructure.output.entity.id.RecipeStepEntityId;
import org.springframework.stereotype.Component;

@Component
public class RecipeStepOutputMapper {

    public RecipeStepEntity modelToEntity (RecipeStep model) {
        RecipeStepEntityId id = new RecipeStepEntityId();
        id.setStepNumber(model.getStepNumber());

        RecipeStepEntity entity = new RecipeStepEntity();
        entity.setId(id);
        entity.setDescription(model.getDescription());
        return entity;
    }

    public RecipeStep entityToModel (RecipeStepEntity entity) {
        RecipeStep model = new RecipeStep();
        model.setStepNumber(model.getStepNumber());
        model.setDescription(entity.getDescription());
        return model;
    }
}
