package kke.travelplan;

import java.util.Date;

public class Plan {
    private String name;

    private Date startDate;

    private Date endDate;

    public Plan() {
    }

    public Plan(String name, Date date) {
        this.name = name;
        this.startDate = date;
        this.endDate = date;
    }

    public Plan(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
