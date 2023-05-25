package com.groupeun.recipe.infrastructure.configuration;

import com.groupeun.recipe.application.ports.input.RecipeInputPort;
import com.groupeun.recipe.application.ports.input.SettingInputPort;
import com.groupeun.recipe.application.ports.output.*;
import com.groupeun.recipe.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public SettingInputPort settingInputPort (SettingOutputPort settingOutputPort) {
        return new SettingService(settingOutputPort);
    }

    @Bean
    public IngredientService ingredientService (ProductService productService, IngredientOutputPort ingredientOutputPort) {
        return new IngredientService(productService, ingredientOutputPort);
    }

    @Bean
    public ProductService productService (ProductOutputPort productOutputPort) {
        return new ProductService(productOutputPort);
    }

    @Bean
    public RecipeStepService recipeStepService (RecipeStepOutputPort recipeStepOutputPort) {
        return new RecipeStepService(recipeStepOutputPort);
    }
    @Bean
    public RecipeInputPort recipeInputPort (RecipeOutputPort recipeOutputPort, IngredientService ingredientService, RecipeStepService recipeStepService) {
        return new RecipeService(recipeOutputPort, ingredientService, recipeStepService);
    }

}
