package com.groupeun.recipe.domain.exception;

import java.util.UUID;

public class RecipeNotFound extends DomainException {
    public RecipeNotFound() {
        super("Recipe not found");
    }

    public RecipeNotFound(UUID id) {
        super(String.format("Recipe[id=%s] not found", id));
    }
}
