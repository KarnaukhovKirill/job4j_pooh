package ru.job4j.pooh;

public enum HttpStatuses {
    HTTP_STATUS_OK,
    HTTP_STATUS_NO_CONTENT,
    HTTP_STATUS_NOT_ALLOWED;

    public String getStatus() {
        if (this == HttpStatuses.HTTP_STATUS_OK) {
            return "200 OK";
        } else if (this == HTTP_STATUS_NO_CONTENT) {
            return "204 NO CONTENT";
        } else  if (this == HTTP_STATUS_NOT_ALLOWED) {
            return "405 METHOD NOT ALLOWED";
        } else {
            return null;
        }
    }
}
