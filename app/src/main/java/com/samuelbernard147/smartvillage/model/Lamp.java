package com.samuelbernard147.smartvillage.model;

import org.json.JSONArray;

public class Lamp {
    private int id;
    private String name;
    private Boolean status;
    private int power;
    private String updatedAt;

    public Lamp(JSONArray object, int posisi) {
        try {
            int id = object.getJSONObject(posisi).getInt("id");
            int status = object.getJSONObject(posisi).getInt("status");
            int daya = object.getJSONObject(posisi).getInt("daya");
            String update = object.getJSONObject(posisi).getString("updated_at");

            this.id = id;
            this.status = status == 1;
            this.power = daya;
            this.updatedAt = update;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}