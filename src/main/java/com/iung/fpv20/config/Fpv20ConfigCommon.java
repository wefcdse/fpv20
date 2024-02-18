package com.iung.fpv20.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.nio.file.Files;
import java.nio.file.Path;

public class Fpv20ConfigCommon {
    private static String CONFIG_PATH = "./config/fpv20_common.json";

    private float step_height = 1.2f;
    private boolean is_in_forge = false;

    public boolean in_forge() {
        return this.is_in_forge;
    }

    public boolean in_fabric() {
        return !this.is_in_forge;
    }

    public float step_height() {
        return step_height;
    }

    private String to_json() {
        Gson j = new GsonBuilder().setPrettyPrinting().create();

        return j.toJson(this);
    }

    private static Fpv20ConfigCommon from_json(String json) {
        Gson j = new Gson();

        return j.fromJson(json, Fpv20ConfigCommon.class);
    }

    public static Fpv20ConfigCommon createAndLoad() {
        try {
            String content = Files.readString(Path.of(CONFIG_PATH));
            return from_json(content);
        } catch (Exception ignored) {
            return new Fpv20ConfigCommon();
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
