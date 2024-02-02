package com.iung.fpv20.gui.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class Slider1 extends SliderWidget {

    Consumer<Slider1> onUpdateMessage;
    Consumer<Slider1> onApplyValue;

    public Slider1(int x, int y, int width, int height, Text text, double value, Consumer<Slider1> onUpdateMessage, Consumer<Slider1> onApplyValue) {


        super(x, y, width, height, text, value);

        this.onUpdateMessage = onUpdateMessage;
        this.onApplyValue = onApplyValue;
    }

    @Override
    protected void updateMessage() {
//        Fpv20.LOGGER.info("updateMessage");
        this.onUpdateMessage.accept(this);
    }

    @Override
    protected void applyValue() {
//        Fpv20.LOGGER.info("applyValue:{}", this.value);
        this.onApplyValue.accept(this);

    }

   public float value(){
        return (float) this.value;
    }
}
