package kke.travelplan.util;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

public class JsonHttpUtil {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static JsonResponse get(String url) {
        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();
        return request(req);
    }

    public static JsonResponse post(String url, String requestJson) {
        RequestBody body = RequestBody.create(JSON, requestJson);
        Request req = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request(req);
    }

    public static JsonResponse request(Request req) {
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(req).execute();
            if (response.code() != HttpURLConnection.HTTP_OK) {
                return JsonResponse.error(response.code(), "HTTP 에러: " + response.code());
            }
            ResponseBody respBody = response.body();
            Map<String, Object> map = objectMapper.readValue(respBody.charStream(), Map.class);
            return JsonResponse.from(map);
        } catch (IOException e) {
            throw new RuntimeException("IO 에러남", e);
        }
    }

    public static String json(Map<String, ? extends Object> map){
        try {
            String s = objectMapper.writeValueAsString(map);
            return s;
        } catch (JsonProcessingException e) {
           throw new RuntimeException("Json문법오류",e);
        }
    }
}
