package kke.travelplan;

import android.util.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import kke.travelplan.util.DateFormats;

public class Plan {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy. M. d");

    private int id;

    private String name;

    private String location;

    private Date startDate;

    private Date endDate;

    private String accountName;

    private boolean public_;

    private int likeCount;

    public Plan() {
    }

    public Plan(String name, Date date) {
        this(name, date, date);
    }

    public Plan(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Plan(String name, Date startDate, Date endDate, String accountName, int likeCount) {
        this(name, startDate, endDate);
        this.accountName = accountName;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPublic() {
        return public_;
    }

    public void setPublic(boolean public_) {
        this.public_ = public_;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getPeriodText() {
        return "<" + df.format(getStartDate()) + " ~ " + df.format(getEndDate()) + ">";
    }

    public String toJson() {
        try {
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.beginObject();
            jsonWriter.name("title").value(this.getName());
            jsonWriter.name("location").value(this.getLocation());
            jsonWriter.name("start_date").value(DateFormats.date.format(this.getStartDate()));
            jsonWriter.name("end_date").value(DateFormats.date.format(this.getEndDate()));
            jsonWriter.name("is_public").value(this.isPublic());
            jsonWriter.endObject();
            jsonWriter.close();
            return stringWriter.toString();
        } catch (IOException e) {
            // 있을 수 없음
            throw new RuntimeException("있을 수 없는 에러가 발생했다.", e);
        }
    }
}
