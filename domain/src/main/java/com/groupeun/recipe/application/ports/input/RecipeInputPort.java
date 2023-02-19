package com.groupeun.recipe.application.ports.input;

import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RecipeInputPort {

    Recipe findOne (UUID id);
    List<Recipe> findAllByNameRegex (String nameRegex);
    List<Recipe> findAll ();
    List<Recipe> search (String nameRegex, int preparationTime);

    Recipe create (Recipe recipe);
    Recipe create (String name,String description, double nutritionalScore, int preparationTime, UUID authorId, Set<RecipeStep> steps);
    Recipe update (Recipe recipe);
    Recipe update (UUID id, String name,String description, double nutritionalScore, int preparationTime, UUID authorId, Set<RecipeStep> steps);

    void delete (Recipe recipe);
    void delete (UUID id);

}
