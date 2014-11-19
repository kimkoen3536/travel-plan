package kke.travelplan.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NaverApi {
    public static List<LocalItem> localSearch(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String url = "http://openapi.naver.com/search" +
                    "?key=e850ce60cf990846cf358db3f6f22185" +
                    "&target=local" +
                    "&display=20" +
                    "&query="  + encodedQuery;
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            InputStream input = conn.getInputStream();
            return parse(input);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static List<LocalItem> parse(InputStream input) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(input);
            NodeList items = doc.getElementsByTagName("item");
            List<LocalItem> list = new ArrayList<LocalItem>();
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                LocalItem localItem = new LocalItem();
                NodeList childNodes = item.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) childNode;
                        if (element.getTagName().equals("title")) {
                            localItem.setTitle(element.getTextContent().trim());
                        } else if (element.getTagName().equals("address")) {
                            localItem.setAddress(element.getTextContent().trim());
                        } else if (element.getTagName().equals("roadAddress")) {
                            localItem.setRoadAddress(element.getTextContent().trim());
                        } else if (element.getTagName().equals("mapx")) {
                            localItem.setMapX(Integer.parseInt(element.getTextContent().trim()));
                        } else if (element.getTagName().equals("mapy")) {
                            localItem.setMapY(Integer.parseInt(element.getTextContent().trim()));
                        }
                    }
                } // for j
                list.add(localItem);
            } // for i
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
