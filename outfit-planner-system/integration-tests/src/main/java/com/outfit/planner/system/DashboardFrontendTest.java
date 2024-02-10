package com.outfit.planner.system;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultTestConfig.class)
public class DashboardFrontendTest {

    @Test
    public void test1() {
        String systemPropertyValue = System.getProperty("APP_URL", "http://dashboard-development:4200");
        log.info("[INTEGRATION_TESTS] system property value {}", systemPropertyValue);
        open(systemPropertyValue);

        $(By.cssSelector(".shadow.dashboard-view__header span")).should(Condition.visible);
        SelenideElement header = $(By.cssSelector(".shadow.dashboard-view__header span"));
        header.shouldHave(Condition.text("Your AI powered outfit planner"));
        log.info("[INTEGRATION_TESTS] Header text {}", header.getText());

        log.info("[INTEGRATION_TESTS] Test has been completed successfully");
    }

}
