package com.iung.fpv20.gui.screens;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.consts.Texts;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public abstract class BackableScreen extends Screen {
    public final Screen parent;

    protected BackableScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;

    }

    @Override
    public void close() {
        Fpv20Client.update_config();
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
    }

    public void onOk() {
    }

    public boolean hasOkButton() {
        return false;
    }


    @Override
    protected void init() {
        super.init();
        int i = this.width / 2 - 155;
        int j = i + 160;
        int width = 150;
        int height = 20;

        if (this.hasOkButton()) {
            this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.cancel"), (btn) -> {
                        this.close();
                    })
                    .dimensions(j, this.height - 5 - height, width, height).build());

            this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.ok"), (btn) -> {
                        this.onOk();
                        this.close();
                    })
                    .dimensions(i, this.height - 5 - height, width, height).build());
        } else {
            this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.back"), (btn) -> {
                        this.close();
                    })
                    .dimensions(i, this.height - 5 - height, width * 2 + 10, height).build());

        }

    }
}
