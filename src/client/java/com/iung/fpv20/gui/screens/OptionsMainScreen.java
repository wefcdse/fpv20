package com.iung.fpv20.gui.screens;

import com.iung.fpv20.consts.Texts;
import com.iung.fpv20.flying.GlobalFlying;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class OptionsMainScreen extends BackableScreen {

    public OptionsMainScreen(Screen parent) {
        super(Texts.TITLE_MAIN_OPTION_SCREEN, parent);
    }

    @Override
    protected void init() {
        super.init();
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;
        int width = 150;
        int height = 20;

        this.addDrawableChild(ButtonWidget.builder(Texts.BTN_SELECT_CONTROLLER, (btn) -> {
                    if (this.client != null) {
                        this.client.setScreen(new SelectControllerScreen(this));
                    }
                })
                .dimensions(i, k, width, height).build());
        this.addDrawableChild(ButtonWidget.builder(Texts.BTN_CALIBRATE, (btn) -> {
                    if (this.client != null) {
                        this.client.setScreen(new CalibrationScreen(this));
                    }
                })
                .dimensions(j, k, width, height).build());
        k += 24;


        this.addDrawableChild(ButtonWidget.builder(Texts.BTN_SHOW_INPUT, (btn) -> {
                    if (this.client != null) {
                        this.client.setScreen(new ShowInputScreen(this));
                    }
                })
                .dimensions(i, k, width, height).build());
        this.addDrawableChild(ButtonWidget.builder(Texts.BTN_BTN_CONFIG, (btn) -> {
                    if (this.client != null) {
                        this.client.setScreen(new BtnConfigScreen(this));
                    }
                })
                .dimensions(j, k, width, height).build());
        k += 24;


/////////////
        this.addDrawableChild(ButtonWidget.builder(Text.literal("fly"), (btn) -> {
                    GlobalFlying.setFlying(!GlobalFlying.getFlying());
                })
                .dimensions(0, 0, 40, 20).build());

//        this.addDrawable(new TextWidget(i, k, width, height, Text.of("ddd"), this.textRenderer));

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
    }

}
