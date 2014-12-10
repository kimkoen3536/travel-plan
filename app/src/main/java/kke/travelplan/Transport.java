package kke.travelplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by K.eun on 2014-11-30.
 */
public class Transport {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy. M. d");


    private int id;

    private int plan_id;
    private Date plan_date;
    private String departure;
    private String destination;
    private String duration;
    private String type;
    private String memo;


    public Transport() {

    }

    public Transport (String departure, String destination,String type, String duration, String memo) {
        this.departure = departure;
        this.destination = destination;
        this.duration = duration;
        this.type = type;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Transport (int plan_id,Date plan_date, String departure, String destination, String type, String  duration, String memo) {
        this.departure = departure;
        this.plan_date = plan_date;
        this.destination = destination;
        this.duration = duration;
        this.memo = memo;
        this.plan_id = plan_id;
        this.type = type;
    }

    public Date getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(Date plan_date) {
        this.plan_date = plan_date;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
