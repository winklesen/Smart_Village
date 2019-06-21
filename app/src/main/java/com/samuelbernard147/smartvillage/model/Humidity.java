package com.samuelbernard147.smartvillage.model;

import org.json.JSONArray;

public class Humidity {
    private int id;
    private String time;
    private int humidity;

    public Humidity(JSONArray object, int posisi) {
        try {
            int id = object.getJSONObject(posisi).getInt("id");
            String waktu = object.getJSONObject(posisi).getString("created_at");
            int kelembapan = object.getJSONObject(posisi).getInt("ph");

            this.id = id;
            this.time = waktu;
            this.humidity = kelembapan;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}