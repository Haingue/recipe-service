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

    @Override
    public Optional<Recipe> findOne(UUID id) {
        Optional<RecipeEntity> entity = recipeRepository.findById(id.toString());
        if (entity.isPresent())
            return entity.map(RecipeOutputMapper::entityToModel);
        return Optional.empty();
    }

    @Override
    public List<Recipe> findAll() {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .map(RecipeOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findAllByNameRegex(String nameRegex) {
        return StreamSupport.stream(recipeRepository.findAllByNameLikeOrderByName(nameRegex).spliterator(), false)
                .map(RecipeOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> search(String nameRegex, int preparationTime) {
        return StreamSupport.stream(recipeRepository.findAllByNameLikeOrPreparationTimeOrderByNameAscPreparationTimeAsc(nameRegex, preparationTime).spliterator(), false)
                .map(RecipeOutputMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<Recipe> create(UUID id, String name, String description, double nutritionalScore, int preparationTime, UUID authorId) {
        RecipeEntity recipEntity = new RecipeEntity();
        recipEntity.setId(id.toString());
        recipEntity.setName(name);
        recipEntity.setDescription(description);
        recipEntity.setNutritionalScore(nutritionalScore);
        recipEntity.setPreparationTime(preparationTime);
        recipEntity.setAuthorId(authorId.toString());
        recipEntity = recipeRepository.save(recipEntity);
        return Optional.of(RecipeOutputMapper.entityToModel(recipEntity));
    }

    @Override
    @Transactional
    public Optional<Recipe> update(UUID id, String name, String description, double nutritionalScore, int preparationTime, UUID authorId) {
        RecipeEntity recipeEntity = recipeRepository.findById(id.toString())
                .orElseThrow(() -> new RecipeNotFound(id));
        recipeEntity.setName(name);
        recipeEntity.setDescription(description);
        recipeEntity.setNutritionalScore(nutritionalScore);
        recipeEntity.setPreparationTime(preparationTime);
        recipeEntity = recipeRepository.save(recipeEntity);
        return Optional.of(RecipeOutputMapper.entityToModel(recipeEntity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        recipeRepository.deleteById(id.toString());
    }
}
