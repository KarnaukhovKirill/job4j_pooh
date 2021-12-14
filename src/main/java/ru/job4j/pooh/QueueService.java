package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if ("GET".equals(req.httpRequestType())) {
            return methodGet(req);
        } else if ("POST".equals(req.httpRequestType())) {
            return methodPost(req);
        } else {
            return new Resp("", HttpStatuses.HTTP_STATUS_NOT_ALLOWED.getStatus());
        }
    }

    private Resp methodGet(Req req) {
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        var linkedQueue = queue.get(req.getSourceName());
        var text = linkedQueue.poll();
        if (text == null) {
            return new Resp("", HttpStatuses.HTTP_STATUS_NO_CONTENT.getStatus());
        }
        return new Resp(text, HttpStatuses.HTTP_STATUS_OK.getStatus());
    }

    private Resp methodPost(Req req) {
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        queue.get(req.getSourceName()).add(req.getParam());
        return new Resp("", HttpStatuses.HTTP_STATUS_OK.getStatus());
    }
}
