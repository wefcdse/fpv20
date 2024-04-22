package com.iung.fpv20.gui.hud;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.flying.GlobalFlying;
import com.iung.fpv20.input.Controller;
import com.iung.fpv20.utils.Utils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

import java.util.Objects;

public class SticksHud implements HudRenderCallback {
    private ValueProvider t;
    private ValueProvider y;
    private ValueProvider p;
    private ValueProvider r;
    private int size;
    private int padding_between;
    private int padding_down = 10;
    private static final int WHITE = 0Xffffffff;

    public SticksHud() {
        this.size = 40;
        this.padding_between = 10;
        this.padding_down = 11;


        this.t = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("t")), 0f);
        this.y = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("y")), 0f);
        this.p = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("p")), 0f);
        this.r = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("r")), 0f);
    }


    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        // TODO: turn this into a keybind
        if (!Fpv20Client.config.show_osd()) {
            return;
        }

        if (!GlobalFlying.getFlying()) {
            return;
        }
//        int size = 100;
//        int padding_between = 10;
//        int padding_down = 10;


        int window_width = drawContext.getScaledWindowWidth();
        int window_height = drawContext.getScaledWindowHeight();

        int start_x_1 = window_width / 2 - padding_between / 2 - size;
        int start_x_2 = window_width / 2 + padding_between / 2;

        int start_y = window_height - padding_down - size;


//        drawContext.fill(start_x_1, start_y, start_x_1 + size, start_y + size, WHITE);
//        drawContext.fill(start_x_2, start_y, start_x_2 + size, start_y + size, WHITE);

        drawContext.drawVerticalLine(start_x_1 + size / 2, start_y, start_y + size, WHITE);
        drawContext.drawVerticalLine(start_x_2 + size / 2, start_y, start_y + size, WHITE);

        drawContext.drawHorizontalLine(start_x_1, start_x_1 + size, start_y + size / 2, WHITE);
        drawContext.drawHorizontalLine(start_x_2, start_x_2 + size, start_y + size / 2, WHITE);

        fill_centered(drawContext, start_x_1 + size / 2 + y(), start_y + size / 2 - t(), 2, WHITE);
        fill_centered(drawContext, start_x_2 + size / 2 + r(), start_y + size / 2 - p(), 2, WHITE);
    }


    int t() {
        return Math.round(this.t.get() * size - size / 2f);
    }

    int y() {
        return Math.round(this.y.get() * size / 2);
    }

    int p() {
        return Math.round(this.p.get() * size / 2);
    }

    int r() {
        return Math.round(this.r.get() * size / 2);
    }

    static void fill_centered(DrawContext drawContext, int x, int y, int r, int color) {
        drawContext.fill(x - r, y - r, x + r, y + r, color);
    }


    @FunctionalInterface
    public interface ValueProvider {
        float get();
    }
}
