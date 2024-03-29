package com.groupeun.application.output.implement;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.application.ports.output.RecipeStepOutputPort;
import com.groupeun.recipe.domain.model.Ingredient;
import com.groupeun.recipe.domain.model.Product;
import com.groupeun.recipe.domain.model.Recipe;
import com.groupeun.recipe.domain.model.RecipeStep;
import com.groupeun.recipe.domain.service.IngredientService;
import com.groupeun.recipe.domain.service.ProductService;
import com.groupeun.recipe.domain.service.RecipeService;
import com.groupeun.recipe.domain.service.RecipeStepService;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OutputUtils {

    public static RecipeService recipeService;
    public static IngredientService ingredientService;
    public static RecipeStepService recipeStepService;

    static {
        RecipeOutputPort recipeOutputPort = RecipeOutputPortImplement.getInstance();
        RecipeStepOutputPort recipeStepOutputPort = RecipeStepOutputPortImplement.getInstance();
        IngredientOutputPort ingredientOutputPort = IngredientOutputPortImplement.getInstance();
        ProductService productService = new ProductService(ProductServiceImplement.getInstance());

        ingredientService = new IngredientService(productService, ingredientOutputPort);
        recipeStepService = new RecipeStepService(recipeStepOutputPort);
        recipeService = new RecipeService(recipeOutputPort, ingredientService, recipeStepService);
    }

    public static void initializeOutputPortImplement () {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            Set<RecipeStep> steps = RecipeStepOutputPortImplement.createRecipeStepList(4);
            Set<Ingredient> ingredients = IngredientOutputPortImplement.createIngredientList(3);
            Recipe recipe = RecipeOutputPortImplement.createRecipe(
                    UUID.randomUUID(),
                    generateString(7),
                    generateString(150),
                    random.nextInt(),
                    random.nextDouble(),
                    UUID.randomUUID(),
                    steps,
                    ingredients
            );
            RecipeStepOutputPortImplement.getStore().put(recipe.getId(), steps);
            IngredientOutputPortImplement.getStore().put(recipe.getId(), ingredients);
            RecipeOutputPortImplement.getStore().put(recipe.getId(), recipe);
        }
    }

    public static String generateString (int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

}
