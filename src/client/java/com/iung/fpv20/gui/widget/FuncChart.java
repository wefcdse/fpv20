package com.iung.fpv20.gui.widget;

import net.minecraft.client.gui.DrawContext;

public class FuncChart extends AbstractChart {
    AbstractChart.Curve curve;

    public FuncChart(int x, int y, int width, int height, AbstractChart.Curve curve) {
        super(x, y, width, height);
        this.curve = curve;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        clear(context, 0xff_00_00_00);
        drawCurve(context, this.curve, 0xff_ff_ff_ff);
        border(context, 0xff_ff_00_00);
    }
}
