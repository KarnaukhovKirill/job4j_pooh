package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import static ru.job4j.pooh.PoohServer.HTTP_STATUS_NO_CONTENT;
import static ru.job4j.pooh.PoohServer.HTTP_STATUS_OK;

public class QueueService implements Service {
    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if ("GET".equals(req.httpRequestType())) {
            return methodGet(req);
        }
        if ("POST".equals(req.httpRequestType())) {
            return methodPost(req);
        }
        return new Resp("Тип запроса неизвестен", HTTP_STATUS_NO_CONTENT);

    }

    private Resp methodGet(Req req) {
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        var linkedQueue = queue.get(req.getSourceName());
        var text = linkedQueue.poll();
        if (text == null) {
            return new Resp("", HTTP_STATUS_NO_CONTENT);
        }
        return new Resp(text, HTTP_STATUS_OK);
    }

    private Resp methodPost(Req req) {
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        queue.get(req.getSourceName()).add(req.getParam());
        return new Resp("", HTTP_STATUS_OK);
    }
}
