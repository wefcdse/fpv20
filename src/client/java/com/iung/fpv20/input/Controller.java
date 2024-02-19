package com.iung.fpv20.input;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.network.ChannelUpdatePacket;
import com.iung.fpv20.utils.Calibration;
import com.iung.fpv20.utils.RateMapper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.lwjgl.glfw.GLFW;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Objects;

public class Controller {
    public static int MAX_CHANNELS = 64;

    private int id;
    public float[] floats;
    byte[] bytes;

    public String[] names;
    public String[] btn_names;

    public Calibration[] calibrations;

    public Controller(int id) {
        this.id = id;
        this.calibrations = Fpv20Client.config.calibrations();

        if (calibrations == null || calibrations.length != MAX_CHANNELS) {
            Calibration[] c = new Calibration[MAX_CHANNELS];
            for (int i = 0; i < c.length; i++) {
                c[i] = new Calibration(-1, 1, 0, 0.3f, 0.5f, Calibration.CalibrateMethod.MaxMidMin);
            }
            Fpv20Client.config.calibrations(c);
            this.calibrations = c;
        }

        this.names = Fpv20Client.config.stick_channel_names();
        if (this.names == null || this.names.length != MAX_CHANNELS) {
            String[] names = new String[MAX_CHANNELS];
            for (int i = 0; i < MAX_CHANNELS; i++) {
                names[i] = String.format("CH%d", i);
            }
            Fpv20Client.config.stick_channel_names(names);
            this.names = names;
        }


        this.btn_names = Fpv20Client.config.button_channel_names();

        if (this.btn_names == null || this.btn_names.length != MAX_CHANNELS) {
            String[] btn_names = new String[MAX_CHANNELS];
            for (int i = 0; i < MAX_CHANNELS; i++) {
                btn_names[i] = String.format("BTN%d", i);
            }
            Fpv20Client.config.button_channel_names(btn_names);
            this.btn_names = btn_names;
        }


    }

//    public Controller(int id) {
//        this.id = id;
//        this.calibrations = new Calibration[8];
//        for (int i = 0; i < 8; i++) {
//            calibrations[i] = new Calibration();
//        }
//        this.names = new String[8];
//        for (int i = 0; i < 8; i++) {
//            names[i] = String.format("CH%d", i);
//        }
//        names[0] = "r";
//        names[1] = "p";
//        names[2] = "t";
//        names[4] = "y";
//
//        calibrations[2].calibrateMethod = Calibration.CalibrateMethod.MaxMin;
//
//
//        this.btn_names = new String[8];
//        for (int i = 0; i < 8; i++) {
//            btn_names[i] = String.format("BTN%d", i);
//        }
//        btn_names[0] = "sw";
//
//
//    }

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
        {
            int length = Math.min(this.floats.length, this.names.length);

            for (int i = 0; i < length; i++) {
                ChannelUpdatePacket p = new ChannelUpdatePacket(this.names[i], this.get_calibrated_value(i));
                ClientPlayNetworking.send(p);
            }
        }
        {
            int length = Math.min(this.bytes.length, this.btn_names.length);

            for (int i = 0; i < length; i++) {
                ChannelUpdatePacket p = new ChannelUpdatePacket(this.btn_names[i], this.get_btn(i));
                ClientPlayNetworking.send(p);
            }
        }

    }

    public float[] getFloatsRaw() {
        return Objects.requireNonNullElseGet(this.floats, () -> new float[0]);
    }

    public float get_calibrated_value(int channel) {
        if (floats == null) {
            return 0;
        }
        if (channel > floats.length || channel < 0) {
            return 0;
        }
        float value = floats[channel];
        return calibrations[channel].map(value);
    }

    public float get_calibrated_value_no_rate(int channel) {
        if (floats == null) {
            return 0;
        }
        if (channel > floats.length || channel < 0) {
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

    public int get_channel_id(String name){
        for (int i = 0; i < this.names.length; i++) {
            if (name.equals(this.names[i])) {
                return i;
            }
        }
        return -1;
    }

    public String get_btn_name(int channel) {
        if (channel >= bytes.length || channel < 0) {
            return "";
        }
        return btn_names[channel];
    }

    public float get_value_by_name(String name) {
        for (int i = 0; i < this.names.length; i++) {
            if (name.equals(this.names[i])) {
                return this.get_calibrated_value(i);
            }
        }

        for (int i = 0; i < this.btn_names.length; i++) {
            if (name.equals(this.btn_names[i])) {
                return this.get_btn(i);
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

    public void set_btn_name(int channel, String name) {
        if (channel >= bytes.length || channel < 0) {
            return;
        }
        if (name.isBlank()) {
            return;
        }
        btn_names[channel] = name.replaceAll("\\s+", "_");
    }

    public float get_btn(int channel) {
        if (channel > this.bytes.length) {
            return 0;
        } else {
            return this.bytes[channel];
        }
    }

    public int get_btn_channels() {
        return this.bytes.length;
    }
}
