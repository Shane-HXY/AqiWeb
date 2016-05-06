package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by huangxiangyu on 16/5/5.
 */
public class PersonalData {
    private int id;
    private double lon;
    private double lat;
    private String city;
    private Timestamp time;
    private double pm2_5;
    private double pm10;
    private int uid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
