package com.groupeun.recipe.infrastructure.output.service;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.infrastructure.output.entity.IngredientEntity;
import com.groupeun.recipe.infrastructure.output.entity.id.IngredientEntityId;
import com.groupeun.recipe.infrastructure.output.mapper.IngredientOutputMapper;
import com.groupeun.recipe.infrastructure.output.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientAdapter implements IngredientOutputPort {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Set<Ingredient> findAllProductByRecipe(UUID recipeId) {
        return ingredientRepository.findAllByIdRecipeId(recipeId.toString())
                .stream().map(IngredientOutputMapper::entityToModel)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Ingredient save (UUID recipeId, UUID ingredientId, int quantity) {
        IngredientEntityId ingredientEntityId = new IngredientEntityId();
        ingredientEntityId.setRecipeId(recipeId.toString());
        ingredientEntityId.setIngredientId(ingredientId.toString());

        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(ingredientEntityId);
        ingredientEntity.setQuantity(quantity);

        return IngredientOutputMapper.entityToModel(ingredientRepository.save(ingredientEntity));
    }

    @Override
    public void delete(UUID recipeId, UUID ingredientId) {
        IngredientEntityId ingredientEntityId = new IngredientEntityId();
        ingredientEntityId.setRecipeId(recipeId.toString());
        ingredientEntityId.setIngredientId(ingredientId.toString());
        ingredientRepository.deleteById(ingredientEntityId);
    }
}
