package com.iung.fpv20.gui.screens;

import com.iung.fpv20.consts.TranslateKeys;
import com.iung.fpv20.gui.widget.FuncChart;
import com.iung.fpv20.gui.widget.Slider1;
import com.iung.fpv20.utils.RateMapper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class RateScreen extends BackableScreen {
    RateMapper mapper;

    protected RateScreen(Screen parent, RateMapper mapper) {
        super(Text.literal("rate"), parent);
        this.mapper = mapper;
    }

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
        addDrawable(new FuncChart(chart_left_padding, chart_top_padding, chart_height_width, chart_height_width,
                x -> this.mapper.map(x)
        ));

        int slide_start = chart_left_padding + chart_height_width + padding;

        addDrawableChild(new Slider1(slide_start,
                30, this.width - slide_start - padding, height, Text.translatable(TranslateKeys.RATE_1, 0.5), 0.5,
                slider -> {
                    slider.setMessage(Text.translatable(TranslateKeys.RATE_1, slider.value()));
                },
                slider -> {
                    this.mapper.a = slider.value();
                }
        ));
        addDrawableChild(new Slider1(slide_start,
                30 + padding + height, this.width - slide_start - padding, height, Text.translatable(TranslateKeys.RATE_1, 0.5), 0.5,
                slider -> {
                    slider.setMessage(Text.translatable(TranslateKeys.RATE_1, slider.value()));
                },
                slider -> {
                    this.mapper.b = slider.value();
                }
        ));

    }

}
