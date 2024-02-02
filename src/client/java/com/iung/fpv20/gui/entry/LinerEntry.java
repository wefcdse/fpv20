package com.iung.fpv20.gui.entry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class LinerEntry extends ElementListWidget.Entry<LinerEntry> {
    public List<ClickableWidget> elements;

    public LinerEntry() {
        this.elements = new ArrayList<>();
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return this.elements;
    }



    @Override
    public List<? extends Element> children() {
        return this.elements;
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        this.elements.forEach((e) -> {
            e.setY(y);
            e.render(context, mouseX, mouseY, tickDelta);
        });

    }
}
