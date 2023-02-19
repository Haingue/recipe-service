package com.groupeun.recipe.infrastructure.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupeun.recipe.infrastructure.input.dto.RecipeDto;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.PostConstruct;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {

    private String endpoint = "/services/recipe";
    private String token;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    private void initialize () {
        token = AuthenticationHelper.getAuthenticationToken();
    }

    @Test
    @Order(1)
    void testContextLoading () throws Exception {
        assertThat(mvc).isNotNull();
        assertThat(objectMapper).isNotNull();
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
    }

    @Test
    @Order(2)
    void testGetAllRecipe () throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andExpect(status().isNoContent());
    }


    @Test
    @Order(3)
    void testNewRecipe () throws Exception {
        UUID authorId = UUID.randomUUID();
        RecipeDto dto = new RecipeDto();
        dto.setName("Recipe 1");
        dto.setDescription("Description test");
        dto.setNutritionalScore(5D);
        dto.setPreparationTime(15);
        dto.setAuthorId(authorId);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is(dto.getName())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(dto.getDescription())))
                .andExpect(jsonPath("$.nutritionalScore", CoreMatchers.is(dto.getNutritionalScore())))
                .andExpect(jsonPath("$.preparationTime", CoreMatchers.is(dto.getPreparationTime())))
                .andExpect(jsonPath("$.authorId", CoreMatchers.is(dto.getAuthorId())))
                .andReturn();

        RecipeDto recipeResult = objectMapper.readValue(result.getResponse().getContentAsByteArray(), RecipeDto.class);
        mvc.perform(MockMvcRequestBuilders.get(endpoint+"?recipeId="+recipeResult.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(dto.getId())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(dto.getName())));
    }
}
