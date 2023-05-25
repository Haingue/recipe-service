package com.groupeun.recipe.application.ports.output;

import com.groupeun.recipe.domain.model.Product;

import java.util.Set;
import java.util.UUID;

public interface ProductOutputPort {
    Set<Product> getIngredientDetails (Set<UUID> ingredientIds);
}
