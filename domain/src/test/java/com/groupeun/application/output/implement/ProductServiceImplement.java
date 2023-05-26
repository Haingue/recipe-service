package com.groupeun.application.output.implement;

import com.groupeun.recipe.application.ports.output.ProductOutputPort;
import com.groupeun.recipe.domain.model.Product;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductServiceImplement implements ProductOutputPort {

    private static ProductServiceImplement instance = new ProductServiceImplement();
    private static HashMap<UUID, Product> store;

    private ProductServiceImplement() {
        super();
    }

    public static ProductOutputPort getInstance() {
        return ProductServiceImplement.instance;
    }
    public static HashMap<UUID, Product> getStore() {
        return ProductServiceImplement.store;
    }

    @Override
    public Set<Product> getIngredientDetails(Set<UUID> productIdList) {
        return productIdList.stream().map(productId -> store.get(productId)).collect(Collectors.toSet());
    }
}
