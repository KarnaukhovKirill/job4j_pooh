package ru.job4j.pooh;

/**
 * Класс служит для парсинга входящего запроса
 */
public class Req {
    /**
     * Тип запроса. GET или POST
     */
    private final String httpRequestType;
    /**
     * Указывает на режим работы. queue или topic
     */
    private final String poohMode;
    /**
     * Имя очереди или топика
     */
    private final String sourceName;
    /**
     * Содержимое запроса
     */
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    /**
     * Метод служит для парсинга входящего запроса
     * @param content входящий запрос от клиента
     * @return this
     */
    public static Req of(String content) {
        var params = content.split(" |\r?\n");
        var anotherParams = params[1].split("/");
        var httpRequestType = params[0];
        var poohMode = anotherParams[1];
        var sourceName = anotherParams[2];
        var param = "";
        if (anotherParams.length > 3) {
            param = anotherParams[3];
        }
        if (httpRequestType.equals("POST")) {
            param = params[params.length - 1];
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
