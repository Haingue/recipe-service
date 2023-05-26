package com.groupeun.recipe.infrastructure.output.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double nutritionalScore;
    @Column
    private int preparationTime;
    @Column
    private String authorId;

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
