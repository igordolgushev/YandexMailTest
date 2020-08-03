package ru.yandex.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import ru.yandex.settings.Mail;

import static ru.yandex.pages.AuthorizationPage.isAuthorizationPageLoaded;
import static ru.yandex.settings.Init.*;
import static ru.yandex.steps.Step.*;
import static ru.yandex.Log.*;

public class MailPage {

    public MailPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public WebDriver driver;

    private static String title = "Входящие — Яндекс.Почта";

    //Кнопка Написать письмо
    @FindBy(xpath = "//a[contains(@Class,'mail-ComposeButton')]")
    private static WebElement writeMailButton;

    //Отображение имени пользователя (по нему вызывается меню работы с аккаунтом)
    @FindBy(xpath = "//span[contains(@Class,'user-account__name')]")
    private static WebElement nameUser;

    //Поле адреса в письме
    @FindBy(xpath = "//div[contains(@class,'tst-field-to')]/descendant::*[contains(@class,'composeYabbles')]")
    private static WebElement fieldTo;

    //После темы в письме
    @FindBy(xpath = "//input[contains(@class,'ComposeSubject-TextField')]")
    private static WebElement fieldSubject;

    //Поле текста в письме
    @FindBy(xpath = "//div[contains(@class, 'cke_contents_ltr')]")
    private static WebElement fieldTextMail;

    //Кнопка закрытия редактора письма(письмо сохраняется в черновик)
    @FindBy(xpath = "//button[contains(@class, 'controlButtons__btn--close')]")
    private static WebElement buttonCloseLetter;

    //Флаг наличия черновиков
    //@FindBy(css = "span.mail-NestedList-Item-Info-Extras:nth-child(1)")
    //private WebElement draftIndex;

    //Папка с черновиками
    @FindBy(xpath = "//a[contains(@data-title, 'Черновики')]")
    private WebElement folderDraft;

    //Сохраненный черновк
    @FindBy(xpath = "//div[contains(@class, 'mail-MessagesList')]/descendant::*[contains(@class, 'mail-MessageSnippet-FromText')][1]")
    private WebElement draftMail;

    //Поле адреса в черновике
    @FindBy(xpath = "//*[contains(@class, 'ComposeYabble_editable')]/descendant::*[contains(@class, 'ComposeYabble-Text')]")
    private WebElement draftTo;

    //Тема в черновика
    @FindBy(xpath = "//input[contains(@name, 'subject')]")
    private WebElement draftSubject;

    //Кнопка выхода из аккаунта
    @FindBy(xpath = "//a[contains(@class, 'legouser__menu-item_action_exit')]")
    private WebElement exitButton;

    //Кнопка отправить письмо
    @FindBy(xpath = "//button[contains(@class, 'ComposeControlPanelButton-Button_action')]")
    private WebElement buttonSend;

    //Всплывающее сообщение "Письмо отправлено"
    @FindBy(xpath = "//div[contains(@class, 'ComposeDoneScreen-Title')]")
    private WebElement showMessageSuccessfullSent;

    //Папка с отправленными письмами
    @FindBy(xpath = "//a[contains(@data-title, 'Отправленные')]")
    private WebElement folderSendEmail;

    //Кнопка обновления почтового ящика
    @FindBy(className = "svgicon-mail--ComposeButton-Refresh")
    private WebElement updateMail;

    //Отправленное письмо
    @FindBy(xpath = "//div[contains(@class, 'mail-MessagesList')]/descendant::*[contains(@class, 'mail-MessageSnippet-FromText')][1]")
    private WebElement sendMail;

    //Линк для того чтобы развернуть отображение адреса кому отправлено письмо
    @FindBy(xpath = "//div[contains(@class, 'mail-Message-Head-Recievers-More')]")
    private WebElement showAdressTo;

    //Адрес в исходящем письме, кому отправлено
    @FindBy(xpath = "//*[contains(@class, 'mail-User-Name')]")
    private WebElement sentTo;

    //тема отправленного письма
    @FindBy(className = "mail-Message-Toolbar-Subject_message")
    private WebElement sentSubject;

    //Текст отправленного письма
    @FindBy(className = "mail-Message-Body-Content")
    private WebElement sentText;

    public boolean isLoaded() {
        logger.info("Ожидание загрузки страницы");
        //по доступности кнопки отправки письма
        wait.until(ExpectedConditions.elementToBeClickable(writeMailButton));
        logger.info("Страница почтового аккаунта загружена");
        return driver.getTitle().contains(title);
    }

    public boolean checkNameUser() {
        //загружен верный аккаунт
        return nameUser.getText().equals(user.getLogin());
    }
    @Step("Создание письма")
    public void createMail(Mail mail) {
        clickElement(writeMailButton);
        logger.info("Открытие редактора письма");
        Assert.assertTrue(buttonSend.isEnabled());

        logger.info("Заполнение письма данными");
        inputText(fieldTo, mail.getMailTo());
        inputText(fieldSubject, mail.getMailSubject());
        inputText(fieldTextMail, mail.getText());
    }
    @Step("Сохранение письма в черновиках")
    public void saveLetterToDraft() {
        logger.info("Закрываем редактор письма");
        clickElement(buttonCloseLetter);
    }

    public void checkLetter(Mail template, Mail mail) {
        logger.info("Проверка правильности заполнения полей письма");
        Assert.assertTrue(template.equals(mail));
    }
    @Step("Проверка сохраения письма в черновиках")
    public void checkLetterToDraft(Mail mail) {

        logger.info("Переходим в черновики");
        clickElement(folderDraft);

        logger.info("Обновляем почтовый ящик");
        clickElement(updateMail);

        logger.info("Проверяем что последний сохраненный черновик наш");
        Assert.assertTrue(draftMail.getText().equals(mail.getMailTo()));
        logger.info("Открываем черновик");
        clickElement(draftMail);
        checkLetter(mail, new Mail(draftTo.getText(), fieldSubject.getAttribute("value"), fieldTextMail.getText()));
    }
    @Step("Выход из почтового аккаунта")
    public void exitThisPage() {
        clickElement(nameUser);
        clickElement(exitButton);
        logger.info("Выход из почтового ящика на страницу авторизации");
        isAuthorizationPageLoaded();
        logger.info("Выход произведен");
    }
    @Step("Отправка письма")
    public void sendMail() {
        logger.info("Отправляем письмо");
        clickElement(buttonSend);
    }

    @Step("Проверка отправки письма")
    public void checkLetterToSentItems() {
        logger.info("Проверка сообщения об отправке");
        Assert.assertTrue(showMessageSuccessfullSent.isEnabled());
        logger.info("Проверка наличия отправленного письма");
        clickElement(updateMail);
        clickElement(folderSendEmail);
        //clickElement(updateMail);
        String adress = sendMail.getText();
        clickElement(sendMail);
        logger.info("Открываем отправленное письмо");
        checkLetter(template, new Mail(adress, sentSubject.getText(), sentText.getText()));
    }
}
