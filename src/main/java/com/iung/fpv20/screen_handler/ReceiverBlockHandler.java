package com.iung.fpv20.screen_handler;

import com.iung.fpv20.consts.ScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class ReceiverBlockHandler extends ScreenHandler {
    public BlockPos pos;

    public ReceiverBlockHandler(int syncId, PlayerInventory playerInventory, ReceiverBlockHandlerData data) {
        super(ScreenHandlers.RECEIVER_SCREEN_HANDLER, syncId);
        this.pos = data.blockPos();
    }

    public ReceiverBlockHandler(int syncId, BlockPos pos) {
        super(ScreenHandlers.RECEIVER_SCREEN_HANDLER, syncId);
        this.pos = pos;
    }

    public ReceiverBlockHandler(int syncId, PlayerInventory playerInventory, Object o) {
        super(ScreenHandlers.RECEIVER_SCREEN_HANDLER, syncId);
        this.pos = ((PacketByteBuf) o).readBlockPos();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public static record ReceiverBlockHandlerData(BlockPos blockPos) {
        public static final PacketCodec<RegistryByteBuf, ReceiverBlockHandlerData> PACKET_CODEC = PacketCodec.of(((value, buf) -> {
            buf.writeBlockPos(value.blockPos());
        }), buf -> new ReceiverBlockHandlerData(buf.readBlockPos()));
    }
}

