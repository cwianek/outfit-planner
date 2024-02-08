package com.outfit.planner.system;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class OutfitPlannerTestRunner {

    public static void main(String[] args) {
        log.info("[INTEGRATION_TESTS] Running integration tests");

        String url = (args.length > 0) ? args[0] : "http://dashboard-development:4200";

        test1(url);
    }

    private static void test1(String url) {
        open(url);

        $(By.cssSelector(".shadow.dashboard-view__header span")).should(Condition.visible);
        SelenideElement header = $(By.cssSelector(".shadow.dashboard-view__header span"));
        header.shouldHave(Condition.text("Your AI powered outfit planner"));
        log.info("[INTEGRATION_TESTS] Header text {}", header.getText());

        log.info("[INTEGRATION_TESTS] Test has been completed successfully");
    }

}
