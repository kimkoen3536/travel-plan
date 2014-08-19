package kke.travelplan;

import android.app.Application;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class App extends Application {
    public static String urlPrefix;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            InputStream in = this.getClassLoader().getResourceAsStream("config.properties");
            Reader reader = new InputStreamReader(in, "UTF-8");
            Properties props = new Properties();
            props.load(reader);
            String host = props.getProperty("kke.travelplan.host");
            int port = Integer.parseInt(props.getProperty("kke.travelplan.port"));
            String contextPath = props.getProperty("kke.travelplan.context_path");
            StringBuilder sb = new StringBuilder();
            sb.append("http://");
            sb.append(host);
            if (port != 80) {
                sb.append(":");
                sb.append(port);
            }
            sb.append("/");
            sb.append(contextPath.replaceAll("^\\/|\\/$", ""));
            urlPrefix = sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8을 지원하지 않는다는 에러", e);
        } catch (IOException e) {
            throw new RuntimeException("config.properties 읽다가 IO 에러 남", e);
        }
    }
}
