package com.iung.fpv20.input;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.network.ChannelUpdatePacket;
import com.iung.fpv20.utils.Calibration;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.ai.brain.task.BreedTask;
import org.lwjgl.glfw.GLFW;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Controller {
    private int id;
    public float[] floats;
    byte[] bytes;

    String[] names;

    public Calibration[] calibrations;

    public Controller(int id) {
        this.id = id;
        this.calibrations = new Calibration[8];
        for (int i = 0; i < 8; i++) {
            calibrations[i] = new Calibration();
        }
        this.names = new String[8];
        for (int i = 0; i < 8; i++) {
            names[i] = String.format("CH%d", i);
        }
        names[0] = "r";
        names[1] = "p";
        names[2] = "t";
        names[4] = "y";

        calibrations[2].calibrateMethod = Calibration.CalibrateMethod.MaxMin;
    }


    public void poll() {
        FloatBuffer a = GLFW.glfwGetJoystickAxes(id);
        ByteBuffer b = GLFW.glfwGetJoystickButtons(id);

        if (a == null || b == null) {
            return;
        }

        float[] floats = new float[a.remaining()];
        a.get(floats);

        byte[] bytes = new byte[b.remaining()];
        b.get(bytes);

        this.floats = floats;
        this.bytes = bytes;


//        Fpv20.LOGGER.info("{}", floats);
//        Fpv20.LOGGER.info("{}", bytes);

    }

    public void sync_to_server() {
        if (!ClientPlayNetworking.canSend(ChannelUpdatePacket.TYPE)) {
            return;
        }
        int length = Math.min(this.floats.length, this.names.length);

        for (int i = 0; i < length; i++) {
            ChannelUpdatePacket p = new ChannelUpdatePacket(this.names[i], this.get_calibrated_value(i));
            ClientPlayNetworking.send(p);
        }
    }

    public float[] getFloatsRaw() {
        if (this.floats != null) {
            return this.floats;
        } else {
            return new float[0];
        }
    }

    public float get_calibrated_value(int channel) {
        if (floats == null) {
            return 0;
        }
        if (channel > floats.length) {
            return 0;
        }
        float value = floats[channel];
        return calibrations[channel].map(value);
    }

    public float get_calibrated_value_no_rate(int channel) {
        if (floats == null) {
            return 0;
        }
        if (channel > floats.length) {
            return 0;
        }
        float value = floats[channel];
        return calibrations[channel].map_no_rate(value);
    }

    public String get_name(int channel) {
        if (channel >= floats.length || channel < 0) {
            return "";
        }
        return names[channel];
    }

    public float get_calibrated_value(String name) {
        for (int i = 0; i < this.names.length; i++) {
            if (name.equals(this.names[i])) {
                return this.get_calibrated_value(i);
            }
        }
        return 0;
    }


    public void set_name(int channel, String name) {
        if (channel >= floats.length || channel < 0) {
            return;
        }
        if (name.isBlank()) {
            return;
        }
        names[channel] = name.replaceAll("\\s+", "_");
    }
}
