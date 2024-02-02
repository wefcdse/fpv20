package com.iung.fpv20.gui.widget;

import net.minecraft.client.gui.DrawContext;

public class FuncChartWithPoint extends AbstractChart {
    Curve curve;
    ValueGetter point_x;
    ValueGetter point_y;

    public FuncChartWithPoint(int x, int y, int width, int height, Curve curve, ValueGetter point_x,
                              ValueGetter point_y) {
        super(x, y, width, height);
        this.curve = curve;
        this.point_x = point_x;
        this.point_y = point_y;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        clear(context, 0xff_00_00_00);
        drawCurve(context, this.curve, 0xff_ff_ff_ff);



        drawDot(context,this.point_x.get(),this.point_y.get(),0xff_00_ff_ff);

        border(context, 0xff_ff_00_00);
    }
}
