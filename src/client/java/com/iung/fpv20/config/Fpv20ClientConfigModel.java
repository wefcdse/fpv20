package com.iung.fpv20.config;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.utils.Calibration;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = Fpv20.MOD_ID)
@Config(name = "fpv20-config", wrapperName = "Fpv20ClientConfig")
public class Fpv20ClientConfigModel {
    public String[] stick_channel_names;
    public String[] button_channel_names;

    public Calibration[] calibrations;
}
