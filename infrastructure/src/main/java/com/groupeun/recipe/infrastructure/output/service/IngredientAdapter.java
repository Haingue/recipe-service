package com.groupeun.recipe.infrastructure.output.service;

import com.groupeun.recipe.application.ports.output.IngredientOutputPort;
import com.groupeun.recipe.domain.exception.DomainException;
import com.groupeun.recipe.domain.model.Ingredient;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IngredientAdapter implements IngredientOutputPort {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Timer latencyMetrics = Metrics.timer("recipe.infrastructure.ingredient_adapter.latency", "action", "fetchProduct");
    private Counter errorMetrics;
    private WebClient client;

    @Value("${com.groupeun.product.url}")
    private String productServiceUrl;

    @Autowired
    MeterRegistry registry;
    @Autowired
    HttpServletRequest request;

    @PostConstruct
    private void initialize () {
        client = WebClient.builder()
                .baseUrl(productServiceUrl)
                .build();
        errorMetrics = Counter.builder("recipe.infrastructure.ingredient_adapter.error")
                .tag("http_code", "!200").register(registry);
    }

    @Override
    public List<Ingredient> getIngredientDetails(List<UUID> ingredientIds) {
        try {
            logger.debug("Fetch product api: {}", ingredientIds);
            LocalDateTime timestamp = LocalDateTime.now();
            ResponseEntity<List<Ingredient>> response = client
                    .post()
                    .uri("/product/details")
                    .header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION))
                    .body(BodyInserters.fromValue(ingredientIds))
                    .retrieve()
                    .toEntityList(Ingredient.class)
                    .block();
            latencyMetrics.record(Duration.between(timestamp, LocalDateTime.now()));
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            errorMetrics.increment();
            logger.error("Error to collect ingredient details (http code: {})", response.getStatusCode().value());
        } catch (WebClientRequestException ex) {}
        // throw new DomainException(String.format("Error to collect ingredient details (http code: %s)", response.getStatusCode().value()));
        return new ArrayList<Ingredient>();
    }

}
