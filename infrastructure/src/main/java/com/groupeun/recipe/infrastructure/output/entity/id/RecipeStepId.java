package com.groupeun.recipe.infrastructure.output.entity.id;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class RecipeStepId implements Serializable {

    private UUID recipeId;
    private UUID ingredientId;

    public RecipeStepId() {
    }

    public RecipeStepId(UUID recipeId, UUID ingredientId) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UUID recipeId) {
        this.recipeId = recipeId;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(UUID ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeStepId recipeStepId = (RecipeStepId) o;
        return recipeId.equals(recipeStepId.recipeId) && ingredientId.equals(recipeStepId.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId);
    }

    @Override
    public String toString() {
        return "StepId{" +
                "recipeId=" + recipeId +
                ", ingredientId=" + ingredientId +
                '}';
    }
}
