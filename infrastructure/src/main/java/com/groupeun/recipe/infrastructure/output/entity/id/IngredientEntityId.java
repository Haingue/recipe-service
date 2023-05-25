package com.groupeun.recipe.infrastructure.output.entity.id;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Embeddable
public class IngredientEntityId implements Serializable {

    private UUID recipeId;
    private UUID ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientEntityId recipeStepId = (IngredientEntityId) o;
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
