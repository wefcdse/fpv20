package com.iung.fpv20.gui.screens;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.consts.Texts;
import com.iung.fpv20.consts.TranslateKeys;
import com.iung.fpv20.gui.widget.*;
import com.iung.fpv20.input.Controller;
import com.iung.fpv20.utils.Calibration;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class ChannelConfigScreen extends BackableScreen {
    int channel;

    protected ChannelConfigScreen(Screen parent, int channel) {
        super(Text.literal("rate"), parent);
        this.channel = channel;
    }

//    @Override
//    public void close() {
//        Fpv20Client.update_config();
//        super.close();
//    }

    @Override
    protected void init() {
        super.init();

        int i = this.width / 2 - 155;
        int j = i + 160;
        int width = 150 * 2 + 10;
        int height = 20;

        int down = this.height - 5 - height;

        int padding = 5;

        int chart_top_padding = 6;
        int chart_left_padding = 10;
        int chart_height_width = down - chart_top_padding * 2;
        addDrawable(new FuncChartWithPoint(chart_left_padding, chart_top_padding, chart_height_width, chart_height_width,
                x -> {
                    Controller controller = Fpv20Client.controller;
                    if (controller != null) {
                        return controller.calibrations[channel].rate_map(x);
                    }
                    return 0;
                },
                () -> {
                    Controller controller = Fpv20Client.controller;
                    if (controller != null) {
                        return Math.abs(controller.get_calibrated_value_no_rate(channel));
                    }
                    return 0;
                },
                () -> {
                    Controller controller = Fpv20Client.controller;
                    if (controller != null) {
                        return Math.abs(controller.get_calibrated_value(channel));
                    }
                    return 0;
                }
        ));

        int slide_start = chart_left_padding + chart_height_width + padding;

        float start_a = 2;
        float start_b = 2;

        Controller controller1 = Fpv20Client.controller;
        if (controller1 != null) {
            start_a = controller1.calibrations[channel].rate_a;
            start_b = controller1.calibrations[channel].rate_b;
        } else {
            return;
        }

        addDrawableChild(new Slider1(slide_start,
                30, this.width - slide_start - padding, height, Text.translatable(TranslateKeys.RATE_1, start_a), start_a,
                slider -> {
                    slider.setMessage(Text.translatable(TranslateKeys.RATE_1, slider.value()));
                },
                slider -> {
                    Controller controller = Fpv20Client.controller;
                    if (controller != null) {
                        controller.calibrations[channel].rate_a = slider.value();
                    }
                }
        ));
        addDrawableChild(new Slider1(slide_start,
                30 + padding + height, this.width - slide_start - padding, height, Text.translatable(TranslateKeys.RATE_1, start_b), start_b,
                slider -> {
                    slider.setMessage(Text.translatable(TranslateKeys.RATE_1, slider.value()));
                },
                slider -> {
                    Controller controller = Fpv20Client.controller;
                    if (controller != null) {
                        controller.calibrations[channel].rate_b = slider.value();
                    }
                }
        ));


        Text btn_text = null;
        switch (controller1.calibrations[channel].calibrateMethod) {
            case MaxMin -> {
                btn_text = Texts.BTN_CALIBRATE_MAX_MIN;
            }
            case MaxMidMin -> {
                btn_text = Texts.BTN_CALIBRATE_MAX_MID_MIN;
            }

            case Raw -> {
                btn_text = Texts.BTN_CALIBRATE_RAW;
            }

        }
        addDrawableChild(ButtonWidget.builder(btn_text, (btn) -> {
            if (btn.getMessage() == Texts.BTN_CALIBRATE_MAX_MIN) {
                btn.setMessage(Texts.BTN_CALIBRATE_MAX_MID_MIN);
                Controller controller = Fpv20Client.controller;
                if (controller != null) {
                    controller.calibrations[channel].calibrateMethod = Calibration.CalibrateMethod.MaxMidMin;
                }
            } else if (btn.getMessage() == Texts.BTN_CALIBRATE_MAX_MID_MIN) {
                btn.setMessage(Texts.BTN_CALIBRATE_RAW);
                Controller controller = Fpv20Client.controller;
                if (controller != null) {
                    controller.calibrations[channel].calibrateMethod = Calibration.CalibrateMethod.Raw;
                }
            } else {
                btn.setMessage(Texts.BTN_CALIBRATE_MAX_MIN);
                Controller controller = Fpv20Client.controller;
                if (controller != null) {
                    controller.calibrations[channel].calibrateMethod = Calibration.CalibrateMethod.MaxMin;
                }
            }

        }).dimensions(slide_start, 30 + (padding + height) * 2, this.width - slide_start - padding, height).build());


        ////////////////////////////
        int height2 = down - (30 + (padding + height) * 3) - padding;

        this.addDrawable(new Value11Display(slide_start + 5, 30 + (padding + height) * 3, 20, height2, () -> {
            Controller ctrl = Fpv20Client.controller;
            if (ctrl != null) {
                return ctrl.getFloatsRaw()[channel];
            } else {
                return 0;
            }
        }));


        this.addDrawable(new Value11Or01Display(slide_start + 5 * 2 + 20, 30 + (padding + height) * 3, 20, height2, () -> {
            Controller ctrl = Fpv20Client.controller;
            if (ctrl != null) {
                return ctrl.get_calibrated_value_no_rate(channel);
            } else {
                return 0;
            }
        }, () -> {
            Controller ctrl = Fpv20Client.controller;
            if (ctrl != null) {
                if (ctrl.calibrations[channel].calibrateMethod == Calibration.CalibrateMethod.MaxMin) {
                    return Value11Or01Display.Mode.$01;
                }
            }
            return Value11Or01Display.Mode.$11;

        }));

        this.addDrawable(new Value11Or01Display(slide_start + 5 * 3 + 20 * 2, 30 + (padding + height) * 3, 20, height2, () -> {
            Controller ctrl = Fpv20Client.controller;
            if (ctrl != null) {
                return ctrl.get_calibrated_value(channel);
            } else {
                return 0;
            }
        }, () -> {
            Controller ctrl = Fpv20Client.controller;
            if (ctrl != null) {
                if (ctrl.calibrations[channel].calibrateMethod == Calibration.CalibrateMethod.MaxMin) {
                    return Value11Or01Display.Mode.$01;
                }
            }
            return Value11Or01Display.Mode.$11;
        }));


        int tb_start = slide_start + 5 * 4 + 20 * 3;
        String text = controller1.get_name(channel);
        this.addDrawableChild(new TextWidget(
                tb_start, 30 + (padding + height) * 3, this.width - tb_start - padding, height,
                Texts.TEXT_SET_CHANNEL_NAME, this.textRenderer
        ));

//        Fpv20.LOGGER.info("{}",text);
//        this.addDrawable(new Input);
        TextFieldWidget tfw = this.addDrawableChild(new TextFieldWidget(this.textRenderer,
                tb_start, 30 + (padding + height) * 4, this.width - tb_start - padding, height,
                null, Texts.TEXT_SET_CHANNEL_NAME
        ));
        tfw.setText(text);
        tfw.setMaxLength(10);
        tfw.setChangedListener(str -> {
//            Fpv20.LOGGER.info("{}", str);
            Controller controller = Fpv20Client.controller;
            if (controller != null) {
                controller.set_name(channel, str);
            }
        });
    }

}
