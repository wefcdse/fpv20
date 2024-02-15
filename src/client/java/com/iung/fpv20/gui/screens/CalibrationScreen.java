package com.iung.fpv20.gui.screens;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.consts.Texts;
import com.iung.fpv20.consts.TranslateKeys;
import com.iung.fpv20.gui.widget.RenewingText;
import com.iung.fpv20.gui.widget.Value11Display;
import com.iung.fpv20.input.Controller;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.Arrays;

public class CalibrationScreen extends BackableScreen {
    private boolean cc;
    private boolean cr;

    private final float[] max;
    private final float[] min;
    private final float[] mid;

    private int channels;

    @Override
    public boolean hasOkButton() {
        return true;
    }

    @Override
    public void onOk() {
        Controller controller = Fpv20Client.controller;
        if (controller == null) {
            return;
        }
        for (int i = 0; i < this.channels; i++) {
            controller.calibrations[i].max = max[i];
            controller.calibrations[i].min = min[i];
            controller.calibrations[i].mid = mid[i];
        }
    }


    public CalibrationScreen(Screen parent) {

        super(Text.literal("Calibration Screen"), parent);
        this.cr = false;
        this.cc = false;
        Controller controller = Fpv20Client.controller;
        int length = 0;
        if (controller != null) {
            length = controller.getFloatsRaw().length;
        }
        max = new float[length];
        min = new float[length];
        mid = new float[length];

        for (int i = 0; i < length; i++) {
            max[i] = controller.calibrations[i].max;
            min[i] = controller.calibrations[i].min;
            mid[i] = controller.calibrations[i].mid;
        }

        channels = length;
    }

    @Override
    protected void init() {
        super.init();
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;
        int width = 150;
        int height = 20;

        if (client != null) {
            this.addDrawableChild(new TextWidget(i, k, width, height, Texts.BTN_CALIBRATE_CENTER, client.textRenderer));
        }
        this.addDrawableChild(ButtonWidget.builder(Texts.BTN_START, (btn) -> {
                    boolean started = btn.getMessage() == Texts.BTN_START;
                    if (started) {
                        btn.setMessage(Texts.BTN_END);
                        Arrays.fill(this.mid, 0);
                        cc = true;
                    } else {
                        btn.setMessage(Texts.BTN_START);
                        cc = false;
                        this.onOk();
                    }
                })
                .dimensions(j, k, width, height).build());
        k += 24;

        this.addDrawableChild(new TextWidget(i, k, width, height, Texts.BTN_CALIBRATE_RANGE, client.textRenderer));
        this.addDrawableChild(ButtonWidget.builder(Texts.BTN_START, (btn) -> {
                    boolean started = btn.getMessage() == Texts.BTN_START;
                    if (started) {
                        btn.setMessage(Texts.BTN_END);
                        Arrays.fill(this.max, -1);
                        Arrays.fill(this.min, 1);
                        cr = true;
                    } else {
                        btn.setMessage(Texts.BTN_START);
                        cr = false;
                        this.onOk();
                    }
                })
                .dimensions(j, k, width, height).build());
        k += 24;

        int channels_width = 30;
        int channels_step = channels_width + 5;

        for (int i1 = 0; i1 < channels; i1++) {
            int finalI = i1;
            this.addDrawable(new RenewingText(i + i1 * channels_step, k, channels_width, height, () -> {
                Controller controller = Fpv20Client.controller;
                if (controller != null) {
                    float value = this.max[finalI];
                    return Text.literal(String.format("%.2f", value));
                } else {
                    return Text.literal("");
                }
            }, client.textRenderer));
        }
        k += 24;


        for (int i1 = 0; i1 < channels; i1++) {
            int finalI = i1;
            this.addDrawable(new Value11Display(i + i1 * channels_step + 10, k, channels_width - 20, 50, () -> {
                Controller controller = Fpv20Client.controller;
                if (controller != null) {
                    return controller.getFloatsRaw()[finalI];
                } else {
                    return 0;
                }
            }));
        }
        for (int i1 = 0; i1 < channels; i1++) {
            int finalI = i1;
            this.addDrawable(new RenewingText(i + i1 * channels_step, k + 25 - height / 2, channels_width, height, () -> {
                Controller controller = Fpv20Client.controller;
                if (controller != null) {
                    float value = this.mid[finalI];
                    return Text.literal(String.format("%.2f", value));
                } else {
                    return Text.literal("");
                }
            }, client.textRenderer));
        }
        k += 4 + 50;


        for (int i1 = 0; i1 < channels; i1++) {
            int finalI = i1;
            this.addDrawable(new RenewingText(i + i1 * channels_step, k, channels_width, height, () -> {
                Controller controller = Fpv20Client.controller;
                if (controller != null) {
                    float value = this.min[finalI];
                    return Text.literal(String.format("%.2f", value));
                } else {
                    return Text.literal("");
                }
            }, client.textRenderer));
        }
        k += 24;

        for (int i1 = 0; i1 < channels; i1++) {
            int finalI = i1;
            this.addDrawableChild(ButtonWidget.builder(Text.translatable(TranslateKeys.BTN_OPTION), (btn) -> {
                this.client.setScreen(new ChannelConfigScreen(this, finalI));
            }).dimensions(i + i1 * channels_step, k, channels_width, height).build());
        }
        k += 24;

//        this.addDrawable(new Value11Display(40, 40, 30, 50, () -> {
//            Controller controller = Fpv20Client.controller;
//            if (controller != null) {
//
//                try {
//                    return controller.floats[0];
//                } catch (NullPointerException n) {
//                    return 0;
//                }
//            } else {
//                return 0;
//            }
//        }));
    }

    @Override
    public void tick() {
        super.tick();
        if (cc) {
            calibrate_center_tick();
        }
        if (cr) {
            calibrate_range_tick();
        }

        Controller controller = Fpv20Client.controller;
        if (controller != null) {
            channels = controller.getFloatsRaw().length;
        }

    }

    void calibrate_range_tick() {
        Controller controller = Fpv20Client.controller;
        float[] raw;
        if (controller != null) {
            raw = controller.getFloatsRaw();
        } else {
            return;
        }
        for (int i = 0; i < raw.length; i++) {
            this.min[i] = Math.min(this.min[i], raw[i]);
            this.max[i] = Math.max(this.max[i], raw[i]);
        }
//        Fpv20.LOGGER.info("{}", this.max);
//        Fpv20.LOGGER.info("{}", this.min);

    }

    void calibrate_center_tick() {
        float r = 0.1f;
        Controller controller = Fpv20Client.controller;
        float[] raw;
        if (controller != null) {
            raw = controller.getFloatsRaw();
        } else {
            return;
        }

        for (int i = 0; i < raw.length; i++) {
            this.mid[i] = raw[i] * r + this.mid[i] * (1 - r);
//            this.max[i] = Math.max(this.max[i], raw[i]);
        }

    }

}
