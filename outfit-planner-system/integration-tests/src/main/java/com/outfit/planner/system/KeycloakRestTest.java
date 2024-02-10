package com.outfit.planner.system;

import com.outfit.planner.system.keycloak.KeycloakTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KeycloakTestConfig.class)
//@AutoConfigureWebTestClient
public class KeycloakRestTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void testKeycloakRealmHasBeenImported() {
        Object responseBody = webClient.get()
                .uri("/realms/outfitplanner-realm")
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        log.info("RESPONSE {}", responseBody);
        assertNotNull(responseBody);
    }

}
