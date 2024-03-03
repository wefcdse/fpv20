package com.iung.fpv20.gui.widget;

import com.iung.fpv20.utils.FastMath;
import net.minecraft.client.gui.DrawContext;

public class Value01Display extends AbstractChart {
    ValueGetter valueGetter;

    public Value01Display(int x, int y, int width, int height, ValueGetter valueGetter) {
        super(x, y, width, height);
        this.valueGetter = () -> {
            try {
                return valueGetter.get();
            } catch (Exception e) {
                return 0;
            }
        };
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.clear(context, 0xff_ff_ff_ff);
        this.border(context, 0xff_00_00_30);
        float value = FastMath.clamp(valueGetter.get(), 0, 1);
        int h = (int) ((1 - value) * this.height);
        this.fillLocal(context, 0, 0, this.width, h, 0xff_0f_0f_0f);
    }

    @FunctionalInterface
    public interface ValueGetter {
        float get();
    }
}
