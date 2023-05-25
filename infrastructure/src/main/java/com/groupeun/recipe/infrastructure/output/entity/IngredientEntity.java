package com.groupeun.recipe.infrastructure.output.entity;

import com.groupeun.recipe.infrastructure.output.entity.id.IngredientEntityId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ingredient")
public class IngredientEntity {

    @EmbeddedId
    private IngredientEntityId id;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientEntity that = (IngredientEntity) o;
        return id.equals(that.id) && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
