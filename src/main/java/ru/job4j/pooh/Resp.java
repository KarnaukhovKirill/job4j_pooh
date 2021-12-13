package ru.job4j.pooh;

/**
 * Класс, описывающий ответ сервиса
 */
public class Resp {
    /**
     * Текст ответа
     */
    private final String text;
    /**
     * HTTP response status codes
     */
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }
}
