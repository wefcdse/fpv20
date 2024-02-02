package com.iung.fpv20.gui.widget;

import com.iung.fpv20.utils.FastMath;
import net.minecraft.client.gui.DrawContext;

import java.util.function.Supplier;

public class Value11Or01Display extends AbstractChart {
    ValueGetter valueGetter;
    Supplier<Mode> modeGetter;

    public Value11Or01Display(int x, int y, int width, int height, ValueGetter valueGetter, Supplier<Mode> modeGetter) {
        super(x, y, width, height);
        this.valueGetter = valueGetter;
        this.modeGetter = modeGetter;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        switch (modeGetter.get()) {

            case $01 -> {
                this.render01(context);
            }
            case $11 -> {
                this.render11(context);
            }
        }

    }

    void render11(DrawContext context) {
        this.clear(context, 0xff_0f_0f_0f);
        this.border(context, 0xff_00_00_30);
        float value = FastMath.clamp(valueGetter.get(), -1, 1);
//        int h = (int) ((1 - value) * this.height);

        if (value < 0) {
            value = -value;
            int end = (int) ((value + 1) / 2 * this.height);
            end = end - this.height / 2;
            this.fillLocal(context, 0, this.height / 2, this.width, end, 0xffffffff);
        } else if (value > 0) {
            int start = (int) ((1 - value) / 2 * this.height);
            this.fillLocal(context, 0, start, this.width, this.height / 2 - start, 0xffffffff);
        }

        if (this.height % 2 == 0) {
            this.fillLocal(context, 0, this.height / 2 - 1, this.width, 2, 0xff_a0_00_00);
        } else {
            this.fillLocal(context, 0, this.height / 2, this.width, 1, 0xff_a0_00_00);
        }
    }

    void render01(DrawContext context) {
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

    public enum Mode {
        $01,
        $11
    }


}
