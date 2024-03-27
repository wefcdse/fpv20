package com.iung.fpv20.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.nio.file.Path;

public class Fpv20ConfigClientManual {
    private static String CONFIG_PATH = "./config/fpv20_client.json";

    private float camera_angle = 35;

    public static class Drone {
        public float mass = 0.5f;
        public float max_force = 16f;
    }
    public Drone drone = new Drone();


    public float getCamera_angle() {
        return camera_angle;
    }

    public void setCamera_angle(float camera_angle) {
        this.camera_angle = camera_angle;
    }


    private String to_json() {
        Gson j = new GsonBuilder().setPrettyPrinting().create();

        return j.toJson(this);
    }

    private static Fpv20ConfigClientManual from_json(String json) {
        Gson j = new Gson();

        return j.fromJson(json, Fpv20ConfigClientManual.class);
    }

    public static Fpv20ConfigClientManual createAndLoad() {
        try {
            String content = Files.readString(Path.of(CONFIG_PATH));
            return from_json(content);
        } catch (Exception ignored) {
            return new Fpv20ConfigClientManual();
        }
    }

    public void save() {
        String json = this.to_json();
        try {
            Files.writeString(Path.of(CONFIG_PATH), json);
        } catch (Exception ignored) {

        }
    }
}
