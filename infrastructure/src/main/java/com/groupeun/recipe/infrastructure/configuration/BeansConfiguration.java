package com.groupeun.recipe.infrastructure.configuration;

import com.groupeun.recipe.application.ports.input.RecipeInputPort;
import com.groupeun.recipe.application.ports.input.SettingInputPort;
import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.application.ports.output.RecipeOutputPort;
import com.groupeun.recipe.application.ports.output.SettingOutputPort;
import com.groupeun.recipe.domain.service.IngredientService;
import com.groupeun.recipe.domain.service.RecipeService;
import com.groupeun.recipe.domain.service.SettingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public SettingInputPort settingInputPort (SettingOutputPort settingOutputPort) {
        return new SettingService(settingOutputPort);
    }

    @Bean
    public IngredientService ingredientService (IngredientOutputPort ingredientOutputPort) {
        return new IngredientService(ingredientOutputPort);
    }

    @Bean
    public RecipeInputPort recipeInputPort (RecipeOutputPort recipeOutputPort, IngredientService ingredientService) {
        return new RecipeService(recipeOutputPort, ingredientService);
    }

}
