package com.groupeun.recipe.infrastructure.output.service;

import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.domain.exception.RecipeNotFound;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.infrastructure.output.entity.RecipeEntity;
import com.groupeun.recipe.infrastructure.output.entity.RecipeStepEntity;
import com.groupeun.recipe.infrastructure.output.mapper.RecipeOutputMapper;
import com.groupeun.recipe.infrastructure.output.mapper.RecipeStepOutputMapper;
import com.groupeun.recipe.infrastructure.output.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeAdapter implements RecipeOutputPort {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeOutputMapper recipeOutputMapper;
    @Autowired
    private RecipeStepOutputMapper recipeStepOutputMapper;

    @Override
    public Optional<Recipe> findOne(UUID id) {
        Optional<RecipeEntity> entity = recipeRepository.findById(id);
        if (entity.isPresent())
            return entity.map(recipeOutputMapper::entityToModel);
        return Optional.empty();
    }

    @Override
    public List<Recipe> findAll() {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .map(recipeOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findAllByNameRegex(String nameRegex) {
        return StreamSupport.stream(recipeRepository.findAllByNameLikeOrderByName(nameRegex).spliterator(), false)
                .map(recipeOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> search(String nameRegex, int preparationTime) {
        return StreamSupport.stream(recipeRepository.findAllByNameLikeOrPreparationTimeOrderByNameAscPreparationTimeAsc(nameRegex, preparationTime).spliterator(), false)
                .map(recipeOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<Recipe> create(UUID id, String name, String description, double nutritionalScore, int preparationTime, UUID authorId, Set<RecipeStep> steps) {
        RecipeEntity recipEntity = new RecipeEntity();
        recipEntity.setId(id);
        recipEntity.setName(name);
        recipEntity.setDescription(description);
        recipEntity.setNutritionalScore(nutritionalScore);
        recipEntity.setPreparationTime(preparationTime);
        recipEntity.setAuthorId(authorId);
        for (RecipeStep step : steps) {
            RecipeStepEntity stepEntity = recipeStepOutputMapper.modelToEntity(step);
            stepEntity.getId().setRecipeId(recipEntity.getId());
            recipEntity.getSteps().add(stepEntity);
        }
        recipEntity = recipeRepository.save(recipEntity);
        return Optional.of(recipeOutputMapper.entityToModel(recipEntity));
    }

    @Override
    @Transactional
    public Optional<Recipe> update(UUID id, String name, String description, double nutritionalScore, int preparationTime, UUID authorId, Set<RecipeStep> steps) {
        RecipeEntity recipeEntity = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFound(id));
        recipeEntity.setName(name);
        recipeEntity.setDescription(description);
        recipeEntity.setNutritionalScore(nutritionalScore);
        recipeEntity.setPreparationTime(preparationTime);
        for (RecipeStep step : steps) {
            RecipeStepEntity stepEntity = recipeStepOutputMapper.modelToEntity(step);
            stepEntity.getId().setRecipeId(recipeEntity.getId());
            recipeEntity.getSteps().add(stepEntity);
        }
        recipeEntity = recipeRepository.save(recipeEntity);
        return Optional.of(recipeOutputMapper.entityToModel(recipeEntity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        recipeRepository.deleteById(id);
    }
}
