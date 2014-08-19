package kke.travelplan.util;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
    Map<String, Object> map = new HashMap<String, Object>();
    private boolean success;
    private int code;
    private String message;

    public JsonResponse() {
        this.map = new HashMap<String, Object>();
    }

    public JsonResponse(Map<String, Object> map) {
        this.success = (Boolean) map.get("success");
        this.code = (Integer) map.get("code");
        this.message = (String) map.get("message");
        this.map = map;
    }

    public static JsonResponse error(int code, String message) {
        JsonResponse resp = new JsonResponse();
        resp.success = false;
        resp.code = code;
        resp.message = message;
        return resp;
    }

    public static JsonResponse from(Map<String, Object> map) {
        return new JsonResponse(map);
    }

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

    public Object get(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }
}
