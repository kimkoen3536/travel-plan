package kke.travelplan;

import java.util.Date;

public class SearchItem {
    private String keyword;

    private Date date;

    public SearchItem() {
    }

    public SearchItem(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {

        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
