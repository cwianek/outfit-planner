package com.outfit.planner.system;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultTestConfig.class)
public class ProductsFrontendTest {

    @Test
    public void test1() {
        String systemPropertyValue = System.getProperty("PRODUCTS_APP_URL", "https://products.outfitplanner.com");

        open(systemPropertyValue);

        $(By.cssSelector(".product-categories")).shouldBe(Condition.visible);
    }

}
