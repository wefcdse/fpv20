package com.iung.fpv20.gui;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.gui.widget.RenewingText;
import com.iung.fpv20.gui.widget.Value01Display;
import com.iung.fpv20.gui.widget.Value11Display;
import com.iung.fpv20.gui.widget.Value11Or01Display;
import com.iung.fpv20.input.Controller;
import com.iung.fpv20.utils.Calibration;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ShowInputScreen extends BackableScreen {
    protected ShowInputScreen(Screen parent) {
        super(Text.literal("show input"), parent);
    }

    @Override
    protected void init() {
        super.init();
        int i = this.width / 2 - 175;
        int j = i + 160;
        int k = this.height / 6 - 12;
        int width = 150;
        int height = 20;

        int channels_width = 10;
        int padding_around = 15;
        int padding_inner = 4;
        int channels_step = channels_width * 2 + padding_around * 2 + padding_inner;
        int text_start = padding_around;
        int text_width = channels_width * 2 + padding_inner;

        int channels;
        float[] floats;
        Controller controller = Fpv20Client.controller;
        if (controller != null) {
            floats = controller.getFloatsRaw();
            channels = floats.length;
        } else {
            return;
        }


        // show stick inputs
        {
            for (int i1 = 0; i1 < channels; i1++) {
                int finalI = i1;
                this.addDrawable(new Value11Display(i + i1 * channels_step + padding_around, k, channels_width, 50, () -> {
                    Controller ctrl = Fpv20Client.controller;
                    if (ctrl != null) {
                        return ctrl.getFloatsRaw()[finalI];
                    } else {
                        return 0;
                    }
                }));

                this.addDrawable(new Value11Or01Display(i + i1 * channels_step + padding_around + padding_inner + channels_width, k, channels_width, 50, () -> {
                    Controller ctrl = Fpv20Client.controller;
                    if (ctrl != null) {
                        return ctrl.get_calibrated_value(finalI);
                    } else {
                        return 0;
                    }
                }, () -> {
                    Controller ctrl = Fpv20Client.controller;
                    if (ctrl != null) {
                        if (ctrl.calibrations[finalI].calibrateMethod == Calibration.CalibrateMethod.MaxMin) {
                            return Value11Or01Display.Mode.$01;
                        }
                    }
                    return Value11Or01Display.Mode.$11;

                }));

                this.addDrawable(new RenewingText(i + i1 * channels_step + text_start, k + 55, text_width, height, () -> {
                    String text = "";
                    Controller controller1 = Fpv20Client.controller;
                    if (controller1 != null) {
                        text = controller1.get_name(finalI);
                    }
                    return Text.literal(text);
                }, this.textRenderer));
            }
        }

        k += 55 + 20 + padding_around;
        // show btn inputs
        channels = controller.get_btn_channels();
        for (int i1 = 0; i1 < channels; i1++) {
            int finalI = i1;
            this.addDrawable(new Value01Display(i + i1 * channels_step + padding_around + (channels_width + padding_inner) / 2, k, channels_width, 50, () -> {
                Controller ctrl = Fpv20Client.controller;
                if (ctrl != null) {
                    return ctrl.get_btn(finalI);
                } else {
                    return 0;
                }
            }));

//            this.addDrawable(new Value01Display(i + i1 * channels_step + padding_around + padding_inner + channels_width, k, channels_width, 50, () -> {
//                Controller ctrl = Fpv20Client.controller;
//                if (ctrl != null) {
//                    return ctrl.get_btn(finalI);
//                } else {
//                    return 0;
//                }
//            }));

            this.addDrawable(new RenewingText(i + i1 * channels_step + text_start, k + 55, text_width, height, () -> {
                String text = "";
                Controller controller1 = Fpv20Client.controller;
                if (controller1 != null) {
                    text = controller1.get_btn_name(finalI);
                }
                return Text.literal(text);
            }, this.textRenderer));
        }


    }
}
