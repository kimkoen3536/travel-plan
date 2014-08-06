package kke.travelplan;


public class SearchResultItem {
    private String name;
    private String address;

    public SearchResultItem(){

    }
    public SearchResultItem(String name,String address) {
        this.name = name;
        this.address = address; }

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



}
