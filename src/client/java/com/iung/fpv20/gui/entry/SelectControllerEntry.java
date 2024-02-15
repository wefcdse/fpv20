package com.iung.fpv20.gui.entry;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.consts.Texts;
import com.iung.fpv20.input.Controller;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class SelectControllerEntry extends LinerEntry {
    private Screen parent_screen;
    private int controller_id;
    private String controller_name;

    public SelectControllerEntry(Screen parent_screen, int controller_id, String controller_name) {
        int padding = 10;
        int btn_width = 30;
        int height = 20;
        this.parent_screen = parent_screen;
        this.controller_id = controller_id;
        this.controller_name = controller_name;
        this.elements.add(ButtonWidget.builder(Text.of(controller_name), (btn) -> {
            Fpv20.LOGGER.info("selected controller {}", controller_id);

            Fpv20Client.controller = new Controller(this.controller_id);
            Fpv20Client.config.setSelected_controller(this.controller_id);
            Fpv20.LOGGER.info("selected controller {}", controller_id);
            parent_screen.close();
        }).dimensions(0, 0, parent_screen.width, height).build());
//        this.elements.add(new TextWidget(padding, 0, parent_screen.width - 3 * padding - btn_width, height, Text.of(controller_name), MinecraftClient.getInstance().textRenderer));


    }



}
