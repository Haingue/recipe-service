package com.groupeun.recipe.domain.exception;

import java.util.UUID;

public class IngredientNotFound extends DomainException {
    public IngredientNotFound() {
        super("Recipe id already used");
    }

    public IngredientNotFound(UUID id) {
        super(String.format("Recipe[id=%s] already used", id));
    }
}
