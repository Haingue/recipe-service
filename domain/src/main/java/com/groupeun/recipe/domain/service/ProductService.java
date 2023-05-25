package com.groupeun.recipe.domain.service;

import com.groupeun.recipe.application.ports.output.ProductOutputPort;
import com.groupeun.recipe.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductService {

    ProductOutputPort productOutputPort;

    public boolean checkIngredientExist (Set<UUID> ingredientIdList) {
        if (ingredientIdList.isEmpty()) return true;
        Set<Product> ingredients = productOutputPort.getIngredientDetails(ingredientIdList);
        return ingredientIdList.size() == ingredients.size();
    }

    public Set<Product> loadIngredientDetails (Set<UUID> ingredientIdList) {
        if (ingredientIdList.isEmpty()) return Collections.emptySet();
        Set<Product> ingredients = productOutputPort.getIngredientDetails(ingredientIdList);
        return ingredients;
    }
}
