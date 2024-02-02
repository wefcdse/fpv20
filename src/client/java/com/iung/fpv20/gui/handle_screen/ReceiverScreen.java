package com.iung.fpv20.gui.handle_screen;

import com.google.common.collect.Lists;
import com.iung.fpv20.Fpv20Client;
import com.iung.fpv20.blocks.ReceiverBlockEntity;
import com.iung.fpv20.consts.Texts;
import com.iung.fpv20.input.Controller;
import com.iung.fpv20.network.SetReceiverPacket;
import com.iung.fpv20.screen_handler.ReceiverBlockHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ReceiverScreen extends HandledScreen<ReceiverBlockHandler> {
    final List<Drawable> drawables = Lists.newArrayList();

    String channel_name;
    boolean neg;

    BlockPos pos;

    public ReceiverScreen(ReceiverBlockHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        BlockPos pos = this.getScreenHandler().pos;
        String channel_name = "";
        boolean if_neg = false;
        if (client != null) {
            if (client.world != null) {
                channel_name = ((ReceiverBlockEntity) Objects.requireNonNull(client.world.getBlockEntity(pos))).channel;
                if_neg = ((ReceiverBlockEntity) Objects.requireNonNull(client.world.getBlockEntity(pos))).neg;
            }
        }
        this.channel_name = channel_name;
        this.neg = if_neg;
        this.pos = pos;
//        this.addDrawableChild(new TextWidget(0, 0, 100, 100, Text.literal(String.format("dasdasdsayudvsauyd %s", channel_name)), this.textRenderer));
/////////////////////////////////////////

        int i = this.width / 2 - 75;
        int width = 150;
        int height = 20;
        int k = this.height / 6 - 12;
        k += 24 * 2;

        TextFieldWidget tfw = this.addDrawableChild(new TextFieldWidget(this.textRenderer,
                i, k, width, height,
                null, Texts.TEXT_SET_CHANNEL_NAME
        ));
        tfw.setText(channel_name);
//        tfw.setMaxLength(10);
        tfw.setChangedListener(str -> {
            this.channel_name = str.replaceAll("\\s+", "_");
        });
        k += 24;

        this.addDrawableChild(ButtonWidget.builder(Text.literal(this.neg ? "-" : "+"), (btn) -> {
            this.neg = !this.neg;
            if (this.neg) {
                btn.setMessage(Text.literal("-"));
            } else {
                btn.setMessage(Text.literal("+"));
            }
        }).dimensions(i, k, width, height).build());
        k += 24;

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), (btn) -> {
            if (ClientPlayNetworking.canSend(SetReceiverPacket.TYPE)) {
                ClientPlayNetworking.send(new SetReceiverPacket(this.channel_name, this.neg, this.pos));
            }
            this.close();
        }).dimensions(i, k, width, height).build());


////////////////////////////////////////////////
    }


    @Override
    protected <T extends Drawable> T addDrawable(T drawable) {
        this.drawables.add(drawable);
        return super.addDrawable(drawable);
    }

    @Override
    protected <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement) {
        this.drawables.add(drawableElement);

        return super.addDrawableChild(drawableElement);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
//        super.render(context, mouseX, mouseY, delta);
        this.drawBackground(context, delta, mouseX, mouseY);
        for (Drawable drawable : this.drawables) {
            drawable.render(context, mouseX, mouseY, delta);
        }


    }
}
