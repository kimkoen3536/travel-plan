package kke.travelplan;


public class SearchResultItem {
    private String name;
    private String address;
    private String roadAddress;
    private int mapX;
    private int mapY;


    public SearchResultItem() {
        this.name = name;
        this.address = address;
        this.roadAddress = roadAddress;}

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

    public String getRoadAddress() {return roadAddress; }

    public void setRoadAddress(String roadAddress) { this.roadAddress = roadAddress; }

    public int getMapX(){ return mapX;}

    public void setMapX(int mapX) {this.mapX = mapX; }

   public int getMapY() {return  mapY;}

    public void setMapY(int mapY) { this. mapY = mapY;}

}
