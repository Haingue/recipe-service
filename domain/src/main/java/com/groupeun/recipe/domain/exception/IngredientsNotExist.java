package com.groupeun.recipe.domain.exception;

public class IngredientsNotExist extends DomainException {
    public IngredientsNotExist() {
        super("All ingredient in steps must be existing in product-service");
    }
}
