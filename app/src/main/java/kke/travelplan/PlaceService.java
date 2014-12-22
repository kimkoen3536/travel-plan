package kke.travelplan;

import java.util.List;
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
        place.setName((String) map.get("name"));
        place.setType((String) map.get("type"));
        place.setId((Integer) map.get("id"));
        place.setAddress((String) map.get("address"));
        place.setRoad_address((String) map.get("road_address"));
        place.setMap_x((Integer) map.get("map_x"));
        place.setMap_y((Integer) map.get("map_y"));
        place.setMemo((String) map.get("memo"));
        List<Integer> intList = (List<Integer>) map.get("picture");
        byte[] bytes = new byte[intList.size()];
        for (int i = 0; i < intList.size(); i++)
            bytes[i] = intList.get(i).byteValue();
        place.setPicture(bytes);
        return place;
    }
}
