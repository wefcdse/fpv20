package com.iung.fpv20.gui.list;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.gui.entry.LinerEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ElementListWidget;

public class SelectControllerList extends ElementListWidget<LinerEntry> {
    public SelectControllerList(MinecraftClient minecraftClient, int width, int left, int top, int bottom, int element_height) {
        super(minecraftClient, width + left, bottom - top, top, bottom, element_height);
        this.left = left;
    }

    @Override
    public int addEntry(LinerEntry entry) {
        return super.addEntry(entry);
    }

//    @Override
//    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
//        super.render(context, mouseX, mouseY, delta);
//        LinerEntry e = this.getEntryAtPosition(mouseX, mouseY);
//        Fpv20.LOGGER.info("{}", e);
//    }

}
