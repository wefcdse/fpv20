package com.iung.fpv20.gui;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.consts.TranslateKeys;
import com.iung.fpv20.gui.widget.Value01Display;
import com.iung.fpv20.input.Controller;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BtnConfigScreen extends BackableScreen {
//    private boolean cc;
//    private boolean cr;

//    private float[] max;
//    private float[] min;
//    private float[] mid;

    private final int channels;

//    @Override
//    public boolean hasOkButton() {
//        return true;
//    }

//    @Override
//    public void onOk() {
//        Controller controller = Fpv20Client.controller;
//        if (controller == null) {
//            return;
//        }
//        for (int i = 0; i < this.channels; i++) {
//            controller.calibrations[i].max = max[i];
//            controller.calibrations[i].min = min[i];
//            controller.calibrations[i].mid = mid[i];
//        }
//    }


    public BtnConfigScreen(Screen parent) {

        super(Text.literal("Calibration Screen"), parent);
//        this.cr = false;
//        this.cc = false;
        Controller controller = Fpv20Client.controller;
        int length = 0;
        if (controller != null) {
            length = controller.get_btn_channels();
        }
//        max = new float[length];
//        min = new float[length];
//        mid = new float[length];

//        for (int i = 0; i < length; i++) {
//            max[i] = controller.calibrations[i].max;
//            min[i] = controller.calibrations[i].min;
//            mid[i] = controller.calibrations[i].mid;
//        }

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

//        if (client != null) {
//            this.addDrawableChild(new TextWidget(i, k, width, height, Texts.BTN_CALIBRATE_CENTER, client.textRenderer));
//        }
//        k += 24;

//        k += 24;

        int channels_width = 30;
        int channels_step = channels_width + 5;

//        for (int i1 = 0; i1 < channels; i1++) {
//            int finalI = i1;
//            this.addDrawable(new RenewingText(i + i1 * channels_step, k, channels_width, height, () -> {
//                Controller controller = Fpv20Client.controller;
//                if (controller != null) {
//                    float value = this.max[finalI];
//                    return Text.literal(String.format("%.2f", value));
//                } else {
//                    return Text.literal("");
//                }
//            }, client.textRenderer));
//        }
        k += 24;


        for (int i1 = 0; i1 < channels; i1++) {
            int finalI = i1;
            this.addDrawable(new Value01Display(i + i1 * channels_step + 10, k, channels_width - 20, 50, () -> {
                Controller controller = Fpv20Client.controller;
                if (controller != null) {
                    return controller.get_btn(finalI);
                } else {
                    return 0;
                }
            }));
        }
//        for (int i1 = 0; i1 < channels; i1++) {
//            int finalI = i1;
//            this.addDrawable(new RenewingText(i + i1 * channels_step, k + 25 - height / 2, channels_width, height, () -> {
//                Controller controller = Fpv20Client.controller;
//                if (controller != null) {
//                    float value = this.mid[finalI];
//                    return Text.literal(String.format("%.2f", value));
//                } else {
//                    return Text.literal("");
//                }
//            }, client.textRenderer));
//        }
        k += 4 + 50;


//        for (int i1 = 0; i1 < channels; i1++) {
//            int finalI = i1;
//            this.addDrawable(new RenewingText(i + i1 * channels_step, k, channels_width, height, () -> {
//                Controller controller = Fpv20Client.controller;
//                if (controller != null) {
//                    float value = this.min[finalI];
//                    return Text.literal(String.format("%.2f", value));
//                } else {
//                    return Text.literal("");
//                }
//            }, client.textRenderer));
//        }
        k += 24;

        for (int i1 = 0; i1 < channels; i1++) {
            int finalI = i1;
            this.addDrawableChild(ButtonWidget.builder(Text.translatable(TranslateKeys.BTN_OPTION), (btn) -> {
                if (this.client != null) {
                    this.client.setScreen(new BtnNameScreen(this, finalI));
                }
            }).dimensions(i + i1 * channels_step, k, channels_width, height).build());
        }
        k += 24;
    }
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
