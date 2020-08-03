package ru.yandex.steps;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import ru.yandex.settings.WebDriverSettings;
import static ru.yandex.Log.*;

public class Step extends WebDriverSettings {

    public static void openPage(String url) {
        driver.get(url);
    }

    public static void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public static void inputText(WebElement element, String text) {
        logger.info("Ввод: {}", text);
        element.clear();
        element.sendKeys(text);
    }

    public static void inputTextAssert(WebElement element, String text) {
        inputText(element, text);
        Assert.assertTrue(element.getAttribute("Value").equals(text));
    }
}