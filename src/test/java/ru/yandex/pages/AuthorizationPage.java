package ru.yandex.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static ru.yandex.Log.*;
import static ru.yandex.settings.Init.*;
import static ru.yandex.steps.Step.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import ru.yandex.settings.User;
import ru.yandex.settings.WebDriverSettings;


public class AuthorizationPage {

    public AuthorizationPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public WebDriver driver;

    //Кнопка выбора процедуры логина
    @FindBy(xpath = "//a[contains(@class,'HeadBanner-Button-Enter')]")
    private WebElement loginButton;

    //Окно ввода логина
    @FindBy (id = "passp-field-login")
    private WebElement inputLogin;

    //Кнопка подтверждения ввода
    @FindBy(xpath = "//button[contains(.,'Войти')]")
    private WebElement enterButton;

    //Окно ввода пароля
    @FindBy (id = "passp-field-passwd")
    private static WebElement inputPassword;

    @Step("Авторизация")
    public void authorization() {
        logger.info("Переход на страницу - {}", PAGEURL);
        openPage(PAGEURL);
        //ожидание загрузки страницы
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));

        logger.info("Авторизация на почтовом сервере");
        clickElement(loginButton);

        logger.info("Ввод логина");
        inputTextAssert(inputLogin, user.getLogin());
        //Клик "Войти"
        clickElement(enterButton);

        logger.info("Ввод пароля");
        inputTextAssert(inputPassword, user.getPassword());
        //Клик "Войти"
        clickElement(enterButton);

        logger.info("Загружаем почтовую страницу");
        Assert.assertTrue(new MailPage(driver).isLoaded());
        logger.info("Проверяем правильность аккаунта (наш пользователь)");
        Assert.assertTrue(new MailPage(driver).checkNameUser());
    }

    public static boolean isAuthorizationPageLoaded() {
        return inputPassword.isEnabled();
    }
}
