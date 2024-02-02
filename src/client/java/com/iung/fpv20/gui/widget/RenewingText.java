package com.iung.fpv20.gui.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RenewingText extends TextWidget {
    private final Supplier<Text> messageGetter;

    public RenewingText(int x, int y, int width, int height, Supplier<Text> messageGetter, TextRenderer textRenderer) {
        super(x, y, width, height, messageGetter.get(), textRenderer);
        this.messageGetter = messageGetter;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.setMessage(this.messageGetter.get());
        super.render(context, mouseX, mouseY, delta);
    }
}
