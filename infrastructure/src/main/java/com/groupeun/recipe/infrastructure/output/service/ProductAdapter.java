package com.groupeun.recipe.infrastructure.output.service;

import com.groupeun.recipe.application.ports.output.ProductOutputPort;
import com.groupeun.recipe.domain.exception.DomainException;
import com.groupeun.recipe.domain.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductAdapter implements ProductOutputPort {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebClient client;

    @Value("${com.groupeun.product.url}")
    private String productServiceUrl;

    @Autowired
    HttpServletRequest request;

    @PostConstruct
    private void initialize () {
        client = WebClient.builder()
                .baseUrl(productServiceUrl)
                .build();
    }

    @Override
    public Set<Product> getIngredientDetails(Set<UUID> ingredientIds) {
        logger.debug("Fetch product api: {}", ingredientIds);
        ResponseEntity<List<Product>> response = client
                .post()
                .uri("/product/details")
                .header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION))
                .body(BodyInserters.fromValue(ingredientIds))
                .retrieve()
                .toEntityList(Product.class)
                .block();
        if (response.getStatusCode().is2xxSuccessful()) {
            return new HashSet<>(response.getBody());
        }
        throw new DomainException(String.format("Error to collect ingredient details (http code: %s)", response.getStatusCode().value()));
    }

}
