package kke.travelplan.util;

import android.util.JsonReader;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.HttpURLConnection;

public class JsonHttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static JsonResponse post(String url, String requestJson) {
        try {
            RequestBody body = RequestBody.create(JSON, requestJson);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.code() != HttpURLConnection.HTTP_OK) {
                return JsonResponse.error(response.code(), "HTTP 에러: " + response.code());
            }

            ResponseBody respBody = response.body();
            JsonReader reader = new JsonReader(respBody.charStream());
            reader.beginObject();
            JsonResponse jsonResp = new JsonResponse();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("success")) {
                    jsonResp.setSuccess(reader.nextBoolean());
                } else if (name.equals("code")) {
                    jsonResp.setCode(reader.nextInt());
                } else if (name.equals("message")) {
                    jsonResp.setMessage(reader.nextString());
                } else {
                    jsonResp.put(name, reader.nextString());
                }
            }
            reader.endObject();
            reader.close();
            return jsonResp;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO 에러남", e);
        }
    }
}
