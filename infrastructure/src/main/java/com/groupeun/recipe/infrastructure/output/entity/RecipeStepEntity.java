package com.groupeun.recipe.infrastructure.output.entity;

import com.groupeun.recipe.infrastructure.output.entity.id.RecipeStepEntityId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recipe_step")
public class RecipeStepEntity {

    @EmbeddedId
    private RecipeStepEntityId id;
    @Column
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeStepEntity that = (RecipeStepEntity) o;
        return id.equals(that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

}
