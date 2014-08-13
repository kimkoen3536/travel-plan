package kke.travelplan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Plan {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy. M. d");

    private String name;

    private Date startDate;

    private Date endDate;

    private String accountName;

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
}
