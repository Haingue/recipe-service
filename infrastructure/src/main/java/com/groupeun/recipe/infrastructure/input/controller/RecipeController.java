package com.groupeun.recipe.infrastructure.input.controller;

import com.groupeun.recipe.application.ports.input.RecipeInputPort;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.infrastructure.input.dto.RecipeDto;
import com.groupeun.recipe.infrastructure.input.mapper.RecipeInputMapper;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/services/recipe")
public class RecipeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecipeInputPort recipeInputPort;
    @Autowired
    private RecipeInputMapper recipeInputMapper;

    @GetMapping
    public ResponseEntity<?> getRecipe (@PathParam("recipeId") @Parameter(name="Recipe Id", allowEmptyValue = true) String recipeId,
                                        @PathParam("nameLike") String nameLike) {
        if (recipeId != null) {
            logger.info("Load one recipe: {}", recipeId);
            Recipe recipe = recipeInputPort.findOne(UUID.fromString(recipeId));
            return ResponseEntity.ok(recipeInputMapper.modelToDto(recipe));
        } else if (nameLike != null) {
            logger.info("Load all recipe with name like: {}", nameLike);
            nameLike = nameLike.replace('*', '%').replace('.', '?');
            List<Recipe> recipes = recipeInputPort.findAllByNameRegex(nameLike);
            if (recipes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(recipeInputPort.findAll().stream()
                    .map(recipeInputMapper::modelToDto).collect(Collectors.toList()));
        } else {
            logger.info("Load all recipe");
            List<Recipe> recipes = recipeInputPort.findAll();
            if (recipes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(recipes.stream()
                    .map(recipeInputMapper::modelToDto).collect(Collectors.toList()));
        }
    }

    @PostMapping
    public ResponseEntity<RecipeDto> addRecipe (@RequestBody RecipeDto recipeDto, Authentication authentication) {
        logger.info("Add new recipe {}: {}", authentication.getName(), recipeDto.getName());
        Recipe recipe = recipeInputPort.create(recipeInputMapper.dtoToModel(recipeDto));
        return new ResponseEntity(recipeInputMapper.modelToDto(recipe), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RecipeDto> updateRecipe (@RequestBody RecipeDto recipeDto, Authentication authentication) {
        logger.info("Update recipe by {}: {}", authentication.getName(), recipeDto.getId());
        Recipe recipe = recipeInputPort.update(recipeInputMapper.dtoToModel(recipeDto));
        return new ResponseEntity(recipeInputMapper.modelToDto(recipe), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity deleteRecipeById (@RequestBody RecipeDto recipeDto, @RequestParam UUID id, Authentication authentication) {
        if (id == null) {
            logger.info("Delete recipe: {}", recipeDto.getId());
            recipeInputPort.delete(recipeDto.getId());
        } else {
            logger.info("Delete recipe: {}", id);
            recipeInputPort.delete(id);
        }
        return ResponseEntity.ok().build();
    }
}
