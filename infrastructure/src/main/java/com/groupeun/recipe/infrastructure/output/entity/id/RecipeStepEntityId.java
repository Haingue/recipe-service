package com.groupeun.recipe.infrastructure.output.entity.id;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class RecipeStepEntityId implements Serializable {

    private String recipeId;
    private int stepNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeStepEntityId recipeStepEntityId = (RecipeStepEntityId) o;
        return recipeId.equals(recipeStepEntityId.recipeId) && stepNumber == recipeStepEntityId.stepNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, stepNumber);
    }

    @Override
    public String toString() {
        return "StepId{" +
                "recipeId=" + recipeId +
                ", stepNumber=" + stepNumber +
                '}';
    }
}
