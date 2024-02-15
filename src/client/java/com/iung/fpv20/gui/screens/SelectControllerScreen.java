package com.iung.fpv20.gui.screens;

import com.iung.fpv20.gui.entry.SelectControllerEntry;
import com.iung.fpv20.gui.list.SelectControllerList;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class SelectControllerScreen extends BackableScreen {

    public SelectControllerScreen(Screen parent) {
        super(Text.literal("SelectControllerScreen"), parent);
    }

    @Override
    protected void init() {
        super.init();
//        OptionListWidget l = new OptionListWidget(this.client, this.width, this.height, 10, 120, 20);
//        l.addOptionEntry(SimpleOption.ofBoolean("a1", true), SimpleOption.ofBoolean("a2", true));
//        l.addOptionEntry(SimpleOption.ofBoolean("a1", true), SimpleOption.ofBoolean("a2", true));
//        l.addOptionEntry(SimpleOption.ofBoolean("a1", true), SimpleOption.ofBoolean("a2", true));
////        this.addDrawableChild(l);


        this.addDrawableChild(selectControllerList());

    }

    private SelectControllerList selectControllerList() {
        SelectControllerList l1 = new SelectControllerList(this.client, this.width, 0, 30, this.height - 30, 40);
//        l1.addEntry(new SelectControllerEntry(this));


        for (int id = 0; id < 16; id++) {
            if (!GLFW.glfwJoystickPresent(id)) {
                continue;
            }
            String name = GLFW.glfwGetJoystickName(id);

            if (name != null) {
                l1.addEntry(new SelectControllerEntry(this, id, name));
            }

        }

        return l1;
//
    }
}
