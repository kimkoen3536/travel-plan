package kke.travelplan;

import android.app.Application;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class App extends Application {
    public static String urlPrefix;

    @Override
    public void onCreate() {
        super.onCreate();
        Config config = ConfigFactory.load();
        String host = config.getString("kke.travelplan.host");
        System.out.println("host : " + host);
        int port = config.getInt("kke.travelplan.port");
        System.out.println("port : " + port);
        String contextPath = config.getString("kke.travelplan.context_path");
        System.out.println("contextPath : " + contextPath);
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
    }
}
