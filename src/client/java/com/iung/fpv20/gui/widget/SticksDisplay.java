package com.iung.fpv20.gui.widget;

import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.flying.GlobalFlying;
import com.iung.fpv20.gui.hud.SticksHud;
import com.iung.fpv20.utils.Utils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.text.Text;

import java.util.Objects;

public class SticksDisplay implements Drawable {
    private final ValueProvider t;
    private final ValueProvider y;
    private final ValueProvider p;
    private final ValueProvider r;
    private final ValueProvider sw;
    private final int size;
    private final int padding_between;
    private final int padding_down;
    private static final int WHITE = 0Xffffffff;
    private static final int YELLOW = 0Xff808000;
    private final TextRenderer textRenderer;


    public SticksDisplay(TextRenderer textRenderer) {
        this.size = 40;
        this.padding_between = 10;
        this.padding_down = 35;

        this.textRenderer = textRenderer;

        this.t = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("t")), 0f);
        this.y = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("y")), 0f);
        this.p = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("p")), 0f);
        this.r = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("r")), 0f);
        this.sw = () -> Utils.requireNonNullOr(Fpv20Client.controller, c -> c.get_calibrated_value_no_rate(c.get_channel_id("sw")), 0f);
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
//        if (!Fpv20Client.config.show_osd()) {
//            return;
//        }

//        if (!GlobalFlying.getFlying()) {
//            return;
//        }
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

        int sw_height = 30;
        int sw_width = 16;
        int sw_border = 2;

        int start_switch_x = start_x_2 + size + 15;
        int start_switch_y = start_y + size / 2 - sw_height / 2;

        drawContext.fill(start_switch_x, start_switch_y, start_switch_x + sw_width, start_switch_y + sw_height, YELLOW);
        if (this.fly()) {
            drawContext.fill(start_switch_x + sw_border, start_switch_y + sw_border, start_switch_x + sw_width - sw_border, start_switch_y + sw_height - sw_border, WHITE);
        }

        int fh = textRenderer.fontHeight;


        drawContext.drawTextWithShadow(textRenderer, "t", start_x_1 + size / 2 - textRenderer.getWidth("t") / 2 + 1, start_y - fh, WHITE);
        drawContext.drawTextWithShadow(textRenderer, "p", start_x_2 + size / 2 - textRenderer.getWidth("p") / 2 + 1, start_y - fh, WHITE);

        drawContext.drawTextWithShadow(textRenderer, "y", start_x_1 - textRenderer.getWidth("y") - 1, start_y + (size - fh) / 2, WHITE);
        drawContext.drawTextWithShadow(textRenderer, "r", start_x_2 + size + 3, start_y + (size - fh) / 2, WHITE);

        drawContext.drawTextWithShadow(textRenderer, "sw", start_switch_x + sw_width / 2 - textRenderer.getWidth("sw") / 2, start_switch_y  - fh -1, WHITE);

    }


    int t() {
        return Fpv20Client.config1.throttle_display_in_center ?
                Math.round(this.t.get() * size / 2f)
                : Math.round(this.t.get() * size - size / 2f);
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

    boolean fly() {
        return this.sw.get() > 0.5f;
    }

    static void fill_centered(DrawContext drawContext, int x, int y, int r, int color) {
        drawContext.fill(x - r, y - r, x + r, y + r, color);
    }


    @FunctionalInterface
    public interface ValueProvider {
        float get();
    }
}
