package ru.yandex.settings;



import static ru.yandex.settings.Init.*;

public class Mail {
    private String mailTo;
    private String mailSubject;
    private String text;

    public Mail(String mailTo, String mailSubject, String text) {
        this.mailTo = mailTo;
        this.mailSubject = mailSubject;
        this.text = text;
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return mailTo.equals(mail.mailTo) &&
                mailSubject.equals(mail.mailSubject) &&
                text.equals(mail.text);
    }
}
