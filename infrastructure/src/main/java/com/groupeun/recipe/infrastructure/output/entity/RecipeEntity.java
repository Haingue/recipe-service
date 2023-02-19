package com.groupeun.recipe.infrastructure.output.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class RecipeEntity {

    @Id
    private UUID id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double nutritionalScore;
    @Column
    private int preparationTime;
    @Column
    private UUID authorId;

    @OneToMany(mappedBy = "id.recipeId", cascade = CascadeType.ALL)
    private Set<RecipeStepEntity> steps = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeEntity that = (RecipeEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
