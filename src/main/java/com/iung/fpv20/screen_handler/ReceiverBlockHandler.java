package com.iung.fpv20.screen_handler;

import com.iung.fpv20.consts.ScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class ReceiverBlockHandler extends ScreenHandler {
    public BlockPos pos;

    public ReceiverBlockHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(ScreenHandlers.RECEIVER_SCREEN_HANDLER, syncId);
        this.pos=buf.readBlockPos();
    }

    public ReceiverBlockHandler(int syncId,BlockPos pos) {
        super(ScreenHandlers.RECEIVER_SCREEN_HANDLER, syncId);
        this.pos = pos;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
