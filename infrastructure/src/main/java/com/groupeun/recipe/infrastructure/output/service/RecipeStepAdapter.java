package com.groupeun.recipe.infrastructure.output.service;

import com.groupeun.recipe.application.ports.output.RecipeStepOutputPort;
import com.groupeun.recipe.application.ports.output.RecipeStepOutputPort;
import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.infrastructure.output.entity.RecipeStepEntity;
import com.groupeun.recipe.infrastructure.output.entity.id.RecipeStepEntityId;
import com.groupeun.recipe.infrastructure.output.mapper.RecipeStepOutputMapper;
import com.groupeun.recipe.infrastructure.output.repository.RecipeStepRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecipeStepAdapter implements RecipeStepOutputPort {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecipeStepRepository RecipeStepRepository;

    @Override
    public Set<RecipeStep> findAllRecipeStepsByRecipe(UUID recipeId) {
        return RecipeStepRepository.findAllByIdRecipeId(recipeId.toString())
                .stream().map(RecipeStepOutputMapper::entityToModel)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public RecipeStep save (UUID recipeId, int stepNumber, String description) {
        RecipeStepEntityId recipeStepEntityId = new RecipeStepEntityId();
        recipeStepEntityId.setRecipeId(recipeId.toString());
        recipeStepEntityId.setStepNumber(stepNumber);

        RecipeStepEntity RecipeStepEntity = new RecipeStepEntity();
        RecipeStepEntity.setId(recipeStepEntityId);
        RecipeStepEntity.setDescription(description);

        return RecipeStepOutputMapper.entityToModel(RecipeStepRepository.save(RecipeStepEntity));
    }

    @Override
    public void delete(UUID recipeId, int stepNumber) {
        RecipeStepEntityId recipeStepEntityId = new RecipeStepEntityId();
        recipeStepEntityId.setRecipeId(recipeId.toString());
        recipeStepEntityId.setStepNumber(stepNumber);
        RecipeStepRepository.deleteById(recipeStepEntityId);
    }
}
