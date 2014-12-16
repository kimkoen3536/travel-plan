package kke.travelplan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by K.eun on 2014-12-16.
 */
public class Favorites {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy. M. d");

    private int plan_id;

    private int user_id;

    public Favorites() {
    }

    public Favorites(int user_id, int plan_id) {
        this.user_id = user_id;
        this.plan_id = plan_id;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
