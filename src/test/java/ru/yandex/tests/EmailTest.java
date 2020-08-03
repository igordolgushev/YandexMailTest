package ru.yandex.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.pages.AuthorizationPage;
import ru.yandex.pages.MailPage;
import ru.yandex.settings.Init;
import ru.yandex.settings.WebDriverSettings;

import static ru.yandex.settings.Init.*;

public class EmailTest extends WebDriverSettings {

    public AuthorizationPage authorizationPage;
    public MailPage mailPage;

    @BeforeClass
    public void setupTest() {
        //загурзка настроечных данных
        Init.setup();
        //Объекты страниц
        authorizationPage = new AuthorizationPage(super.driver);
        mailPage = new MailPage(super.driver);
        //Авторизоваться в системе пользователем
        authorizationPage.authorization();
        //Создаем письмо
        mailPage.createMail(template);
   }

    @Test(description = "Сохранение письма в черновиках")
    public void saveEmailToDratf () {
        //сохранить письмо как черновик
        mailPage.saveLetterToDraft();
        //Открыть папку "Черновики", найти наш черновик, проверить поля «Кому», «Тема» и «Тело» созданного письма
        mailPage.checkLetterToDraft(template);
    }

    @Test(description = "Отправка письма по почте")
    public void sendEmail() {
        //отправить письмо
        mailPage.sendMail();
        //Открыть папку с отправленными письмами и проверить поля «Кому», «Тема» и «Тело» отправленного письма
        mailPage.checkLetterToSentItems();
    }

    @AfterClass
    public void endTest() {
        //Выход из системы с помощью нажатия «Выход»/«Выйти»
        mailPage.exitThisPage();
    }
}
