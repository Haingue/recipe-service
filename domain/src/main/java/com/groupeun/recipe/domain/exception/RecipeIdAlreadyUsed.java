package com.groupeun.recipe.domain.exception;

import java.util.UUID;

public class RecipeIdAlreadyUsed extends DomainException {
    public RecipeIdAlreadyUsed() {
        super("Recipe id already used");
    }

    public RecipeIdAlreadyUsed(UUID id) {
        super(String.format("Recipe[id=%s] already used", id));
    }
}
