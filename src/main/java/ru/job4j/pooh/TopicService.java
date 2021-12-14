package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics
            = new ConcurrentHashMap<>();
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
        topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
        topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
        var linkedQueue = topics.get(req.getSourceName()).get(req.getParam());
        var text = linkedQueue.poll();
        if (text == null) {
            return new Resp("", HttpStatuses.HTTP_STATUS_NO_CONTENT.getStatus());
        }
        return new Resp(text, HttpStatuses.HTTP_STATUS_OK.getStatus());
    }

    private Resp methodPost(Req req) {
        if (topics.get(req.getSourceName()) != null) {
            for (var queue : topics.get(req.getSourceName()).values()) {
                queue.add(req.getParam());
            }
        }
        return new Resp("", HttpStatuses.HTTP_STATUS_OK.getStatus());
    }
}
