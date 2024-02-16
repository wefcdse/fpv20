package com.iung.fpv20.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.nio.file.Files;
import java.nio.file.Path;

public class Fpv20ConfigServer {
    private static String CONFIG_PATH = "./config/fpv20_server.json";

    private float step_height = 1.2f;

    public float step_height() {
        return step_height;
    }

    private String to_json() {
        Gson j = new GsonBuilder().setPrettyPrinting().create();

        return j.toJson(this);
    }

    private static Fpv20ConfigServer from_json(String json) {
        Gson j = new Gson();

        return j.fromJson(json, Fpv20ConfigServer.class);
    }

    public static Fpv20ConfigServer createAndLoad() {
        try {
            String content = Files.readString(Path.of(CONFIG_PATH));
            return from_json(content);
        } catch (Exception ignored) {
            return new Fpv20ConfigServer();
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
