package com.iung.fpv20.globals;

import java.util.HashMap;
import java.util.Map;

public class AllChannels {
    public static Map<String, ChannelInfo> ALL_CHANNELS = new HashMap<>();

    public static class ChannelInfo {

        public float value;

        public ChannelInfo(float value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("value=%f", value);
        }
    }
}
