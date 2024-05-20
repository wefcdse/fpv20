package com.iung.fpv20.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iung.fpv20.utils.Calibration;

import java.nio.file.Files;
import java.nio.file.Path;

public class Fpv20ClientConfig1 {
    private static String CONFIG_PATH = "./config/fpv20.json";

    public String[] stick_channel_names;
    public String[] button_channel_names;
    public Calibration[] calibrations;
//    private float camera_angle = 35;
    private int selected_controller;

    private boolean show_osd = true;

    public int selected_controller() {
        return selected_controller;
    }

    public void setSelected_controller(int selected_controller) {
        this.selected_controller = selected_controller;
    }


//    public float getCamera_angle() {
//        return camera_angle;
//    }
//
//    public void setCamera_angle(float camera_angle) {
//        this.camera_angle = camera_angle;
//    }


    private String to_json() {
        Gson j = new GsonBuilder().setPrettyPrinting().create();

        return j.toJson(this);
    }

    private static Fpv20ClientConfig1 from_json(String json) {
        Gson j = new Gson();

        return j.fromJson(json, Fpv20ClientConfig1.class);
    }

    public static Fpv20ClientConfig1 createAndLoad() {
        try {
            String content = Files.readString(Path.of(CONFIG_PATH));
            return from_json(content);
        } catch (Exception ignored) {
            return new Fpv20ClientConfig1();
        }
    }

    public void save() {
        String json = this.to_json();
        try {
            Files.writeString(Path.of(CONFIG_PATH), json);
        } catch (Exception ignored) {

        }
    }


    public String[] stick_channel_names() {
        return stick_channel_names;
    }

    public String[] button_channel_names() {
        return button_channel_names;
    }

    public Calibration[] calibrations() {
        return calibrations;
    }

    public void stick_channel_names(String[] value) {
        stick_channel_names = value;
    }

    public void button_channel_names(String[] value) {
        button_channel_names = value;
    }

    public void calibrations(Calibration[] value) {
        calibrations = value;
    }

    public boolean show_osd(){ return show_osd; }
    public void setShow_osd(boolean value){ show_osd = value; }
}
