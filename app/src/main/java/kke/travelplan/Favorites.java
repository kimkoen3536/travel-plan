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

    private int user_id;
    private int favor_plan_id;
    private int favor_user_id;

    public Favorites() {
    }

    public Favorites(int user_id, int favor_plan_id, int favor_user_id) {
        this.user_id = user_id;
        this.favor_plan_id = favor_plan_id;
        this.favor_user_id = favor_user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFavor_plan_id() {
        return favor_plan_id;
    }

    public void setFavor_plan_id(int favor_plan_id) {
        this.favor_plan_id = favor_plan_id;
    }

    public int getFavor_user_id() {
        return favor_user_id;
    }

    public void setFavor_user_id(int favor_user_id) {
        this.favor_user_id = favor_user_id;
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
