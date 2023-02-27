package com.example.portal.scheduler.jobs;

import java.util.Map;

public class Message {

    public static String USER_ID_HEADER = "X-User-Id";
    public static String ORIGIN_HEADER = "X-Origin";
    public static String CONTEXT_HEADER = "X-Context-Id";
    public static String REQUEST_HEADER = "X-Request-Id";

    private Map<String, Object> headers;
    private Object payload;

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
