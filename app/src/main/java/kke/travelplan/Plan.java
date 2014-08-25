package kke.travelplan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Plan {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy. M. d");

    private int id;

    private String title;

    private String location;

    private Date startDate;

    private Date endDate;

    private String accountName;

    private boolean public_;

    private int numLikes;

    public Plan() {
    }

    public Plan(String title, Date date) {
        this(title, date, date);
    }

    public Plan(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Plan(String title, Date startDate, Date endDate, String accountName, int numLikes) {
        this(title, startDate, endDate);
        this.accountName = accountName;
        this.numLikes = numLikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPublic_() {
        return public_;
    }

    public void setPublic_(boolean public_) {
        this.public_ = public_;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    @JsonIgnore
    public String getPeriodText() {
        return "<" + df.format(getStartDate()) + " ~ " + df.format(getEndDate()) + ">";
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
