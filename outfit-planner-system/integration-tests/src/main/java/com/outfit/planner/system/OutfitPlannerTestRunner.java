package com.outfit.planner.system;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class OutfitPlannerTestRunner {

    public static void main(String[] args) {
        String url = (args.length > 0) ? args[0] : "https://dashboard.outfitplanner.com";
        log.info("Hello from here arhs {}", url);
        System.out.println("kekekeke helo");

//        try {
            test1(url);
//        }catch (Exception e){
//            System.exit(1);
//        }
    }

    private static void test1(String url) {
        Selenide.sleep(5000);

        open(url);

        SelenideElement header = $(By.cssSelector(".shadow.dashboard-view__header span"));
        header.shouldHave(Condition.text("Your AI powered outfit planner"));
        log.info("[INTEGRATION_TESTS] Header text {}", header.getText());

        log.info("[INTEGRATION_TESTS] Test has been completed successfully");
    }

}
