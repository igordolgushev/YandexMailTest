package ru.yandex.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static ru.yandex.Log.*;

public class Init {

    public static String USER_LOGIN;
    public static String USER_PASSWORD;

    public static String MAIL_TO;
    public static String MAIL_SUBJECT;
    public static String MAIL_TEXT;

    public static String PAGEURL;
    public static int IMPLICITYWAIT = 15;
    public static int PAGELOADTIMEOUT = 20;

    public static User user;
    public static Mail template;

    public static void setup() {



        try(FileInputStream fis = new FileInputStream(new File("src/main/resources/setupData.properties"))) {
            logger.info("Загрузка настроек из файла конфигурации");
            Properties props = new Properties();
            props.load(fis);

            USER_LOGIN = props.getProperty("USER_LOGIN");
            USER_PASSWORD =props.getProperty("USER_PASSWORD");
            MAIL_TO = props.getProperty("MAIL_TO");
            MAIL_SUBJECT = props.getProperty("MAIL_SUBJECT");
            MAIL_TEXT = props.getProperty("MAIL_TEXT");
            PAGEURL = props.getProperty("PAGEURL");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        user = new User(USER_LOGIN, USER_PASSWORD);
        template = new Mail(MAIL_TO, MAIL_SUBJECT, MAIL_TEXT);
    }
}