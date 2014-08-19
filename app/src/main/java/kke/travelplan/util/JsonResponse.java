package kke.travelplan.util;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
    public static JsonResponse error(int code, String message) {
        JsonResponse resp = new JsonResponse();
        resp.success = false;
        resp.code = code;
        resp.message = message;
        return resp;
    }

    private boolean success;

    private int code;

    private String message;

    Map<String, String> map = new HashMap<String, String>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String get(String key) {
        return map.get(key);
    }

    public void put(String key, String value) {
        map.put(key, value);
    }
}
