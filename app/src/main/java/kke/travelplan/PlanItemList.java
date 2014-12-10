package kke.travelplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by K.eun on 2014-12-06.
 */
public class PlanItemList {



    private int tid;
    private int plan_id;
    private Date plan_date;
    private String tdeparture;
    private String tdestination;
    private String tduration;
    private String ttype;

    private int pid;
    private String pname;
    private  String paddress;
    private int map_x;
    private int map_y;
    private String ptype;



    public PlanItemList(int tid, int plan_id, Date plan_date, String tdeparture, String tdestination,
                       String tduration, String ttype){
        this.tdeparture=tdeparture;
        this.tdestination=tdestination;
        this.tid=tid;
        this.plan_date=plan_date;
        this.plan_id=plan_id;
        this.ttype=ttype;
        this.tduration=tduration;

    }

    public PlanItemList(String ttype, String tdeparture,String tdestination, String tduration){
        this.ttype=ttype;
        this.tdeparture=tdeparture;
        this.tdestination=tdestination;
        this.tduration=tduration;
    }

    public PlanItemList(String ptype, String paddress, String pname){
        this.paddress=paddress;
        this.pname=pname;
        this.ptype=ptype;
    }

    public PlanItemList(int pid,int plan_id,Date plan_date,String ptype, String paddress, String pname, int map_x,
                        int map_y){
        this(paddress,pname,ptype);
        this.plan_date=plan_date;
        this.plan_id=plan_id;
        this.map_x=map_x;
        this.map_y=map_y;
        this.pid=pid;
    }

    public PlanItemList() {

    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public Date getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(Date plan_date) {
        this.plan_date = plan_date;
    }

    public String getTdeparture() {
        return tdeparture;
    }

    public void setTdeparture(String tdeparture) {
        this.tdeparture = tdeparture;
    }

    public String getTdestination() {
        return tdestination;
    }

    public void setTdestination(String tdestination) {
        this.tdestination = tdestination;
    }

    public String getTduration() {
        return tduration;
    }

    public void setTduration(String tduration) {
        this.tduration = tduration;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public int getMap_x() {
        return map_x;
    }

    public void setMap_x(int map_x) {
        this.map_x = map_x;
    }

    public int getMap_y() {
        return map_y;
    }

    public void setMap_y(int map_y) {
        this.map_y = map_y;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }
}
