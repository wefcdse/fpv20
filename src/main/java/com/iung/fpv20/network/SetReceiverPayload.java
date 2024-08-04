package com.iung.fpv20.network;

import com.iung.fpv20.Fpv20;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record SetReceiverPayload(String channel_name, boolean neg, BlockPos pos) implements CustomPayload {
    public static final Id<SetReceiverPayload> ID = new Id<>(Identifier.of(Fpv20.MOD_ID, "set_receiver_packet"));
    public static final PacketCodec<PacketByteBuf, SetReceiverPayload> CODEC = PacketCodec.of((value, buf) -> {
        buf.writeString(value.channel_name);
        buf.writeBoolean(value.neg);
        buf.writeBlockPos(value.pos);
    }, buf -> new SetReceiverPayload(buf.readString(), buf.readBoolean(), buf.readBlockPos()));


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
