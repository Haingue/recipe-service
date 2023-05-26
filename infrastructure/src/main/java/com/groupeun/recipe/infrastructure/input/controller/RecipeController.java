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
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/services/recipes")
public class RecipeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecipeInputPort recipeInputPort;
    @Autowired
    private RecipeInputMapper recipeInputMapper;

    @GetMapping("/{recipeId}")
    public ResponseEntity<?> getOneRecipe (@PathVariable String recipeId) {
        logger.info("Load one recipe: {}", recipeId);
        Recipe recipe = recipeInputPort.findOne(UUID.fromString(recipeId));
        return ResponseEntity.ok(recipeInputMapper.modelToDto(recipe));
    }

    @GetMapping
    public ResponseEntity<?> getRecipe (@PathParam("nameLike") @Parameter(name="Name regex")  Optional<String> nameLike) {
        if (nameLike.isPresent()) {
            logger.info("Load all recipe with name like: {}", nameLike);
            List<Recipe> recipes = recipeInputPort.findAllByNameRegex(
                    nameLike
                            .map(regex -> regex.replace('*', '%').replace('.', '?'))
                            .get());
            if (recipes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(recipeInputPort.findAll().stream()
                    .map(recipeInputMapper::modelToDto).collect(Collectors.toList()));
        }
        logger.info("Load all recipe");
        List<Recipe> recipes = recipeInputPort.findAll();
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes.stream()
                .map(recipeInputMapper::modelToDto).collect(Collectors.toList()));
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
        return ResponseEntity.ok(recipeInputMapper.modelToDto(recipe));
    }

    @DeleteMapping
    public ResponseEntity deleteRecipeById (@RequestBody Optional<RecipeDto> recipeDto, @RequestParam Optional<UUID> id, Authentication authentication) {
        if (recipeDto.isPresent()) {
            logger.info("Delete recipe: {}", recipeDto.get().getId());
            recipeInputPort.delete(recipeDto.get().getId());
            return ResponseEntity.ok().build();
        } else if (id.isPresent()) {
            logger.info("Delete recipe: {}", id.get());
            recipeInputPort.delete(id.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
