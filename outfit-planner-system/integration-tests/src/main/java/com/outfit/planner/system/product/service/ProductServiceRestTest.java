package com.outfit.planner.system.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceTestConfig.class)
public class ProductServiceRestTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void testKeycloakRealmHasBeenImported() {
        ParameterizedTypeReference<List<Object>> response = new ParameterizedTypeReference<List<Object>>() {};
        List<Object> responseBody = webClient.get()
                .uri("/api/products/mock")
                .retrieve()
                .bodyToMono(response)
                .block();

        log.info("RESPONSE {}", responseBody);
        assertNotNull(responseBody);
        assertTrue(responseBody.size() > 0);
    }

}
