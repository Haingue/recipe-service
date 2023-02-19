package com.groupeun.recipe.application.ports.output;

import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RecipeOutputPort {

    Optional<Recipe> findOne (UUID id);
    List<Recipe> findAll ();
    List<Recipe> findAllByNameRegex (String nameRegex);
    List<Recipe> search (String nameRegex, int preparationTime);

    Optional<Recipe> create (UUID id, String name,String description, double nutritionalScore, int preparationTime,
                             UUID authorId, Set<RecipeStep> steps);
    Optional<Recipe> update (UUID id, String name,String description, double nutritionalScore, int preparationTime,
                             UUID authorId, Set<RecipeStep> steps);

    void delete (UUID id);


}
