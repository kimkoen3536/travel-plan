package kke.travelplan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

/**
 * Created by K.eun on 2014-12-08.
 */
public class PlaceService {
    private static PlaceService instance;

    public static PlaceService getInstance() {
        if (instance == null) {
            instance = new PlaceService();
        }
        return instance;
    }

    private PlaceService() {
    }

    public Place get(int id) {
        String url = App.urlPrefix + "/place/get2.tpg?id=" + id;
        JsonResponse resp = JsonHttpUtil.get(url);
        Map<String, Object> map = (Map<String, Object>) resp.get("place");
        Place place = new Place();
        place.setPlan_id((Integer)map.get("plan_id"));
        place.setPlan_date((DateFormats.parseDate((String) map.get("plan_date"))));
        place.setName((String)map.get("name"));
        place.setType((String)map.get("type"));
        place.setId((Integer)map.get("id"));
        place.setAddress((String)map.get("address"));
        place.setRoad_address((String)map.get("road_address"));
        place.setMap_x((Integer)map.get("map_x"));
        place.setMap_y((Integer)map.get("map_y"));
        place.setMemo((String)map.get("memo"));

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

           //    byteArray.toByteArray()
        System.out.println("picture2 ::::::::: " + map.get("picture"));
        //place.setPicture((byte[])map.get("picture"));
        return place;
    }
}
