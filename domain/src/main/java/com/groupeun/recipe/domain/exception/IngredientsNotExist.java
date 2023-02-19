package com.groupeun.recipe.domain.exception;

import java.util.UUID;

public class IngredientsNotExist extends DomainException {
    public IngredientsNotExist() {
        super("All ingredient in steps must be existing in product-service");
    }
}
