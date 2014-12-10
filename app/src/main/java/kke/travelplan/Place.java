package kke.travelplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Blob;
import java.util.Date;

/**
 * Created by K.eun on 2014-12-04.
 */
public class Place {

    private int id;

    private int plan_id;

    private Date plan_date;

    private String name;

    private String address;

    private String road_address;

    private int map_x;

    private int map_y;

    private String type;

    private byte[] picture;

    private String memo;

    public Place(){};

    public Place (String name, String address, String road_address, String type) {
        this.name = name;
        this.address = address;
        this.road_address =road_address;
        this.type = type;

    }

    public Place (int plan_id, Date plan_date, String name, String address, String road_address,
                  int map_x, int map_y, String type, byte[] picture, String memo) {
        this(name, address, road_address, type);
        this.plan_id = plan_id;
        this.plan_date = plan_date;
        this.map_x = map_x ;
        this.map_y = map_y;
        this.picture = picture;
        this.memo = memo;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoad_address() {
        return road_address;
    }

    public void setRoad_address(String road_address) {
        this.road_address = road_address;
    }

    public int getMap_x() {
        return map_x;
    }

    public void setMap_x(int map_x) {
        this.map_x = map_x;
    }

    public int getMap_y() {
        return map_y;
    }

    public Date getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(Date plan_date) {
        this.plan_date = plan_date;
    }

    public void setMap_y(int map_y) {
        this.map_y = map_y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toJson() {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json 에러", e);
        }
    }
}
