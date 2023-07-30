package com.groupeun.recipe.domain.exception;

public class IngredientNotValid extends DomainException {
    public IngredientNotValid() {
        super("Ingredient not valid");
    }

    public IngredientNotValid(String attributeName, Object attributeValue) {
        super(String.format("Ingredient not valid (%s=%s)", attributeName, attributeValue));
    }
}
