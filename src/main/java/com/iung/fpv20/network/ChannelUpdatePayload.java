package com.iung.fpv20.network;

import com.iung.fpv20.Fpv20;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ChannelUpdatePayload(String name, float value) implements CustomPayload {
    public static final Id<ChannelUpdatePayload> ID = new CustomPayload.Id<>(Identifier.of(Fpv20.MOD_ID, "channel_update_packet"));
    public static final PacketCodec<PacketByteBuf, ChannelUpdatePayload> CODEC = PacketCodec.of((value, buf) -> {
        buf.writeString(value.name);
        buf.writeFloat(value.value);
    }, buf -> new ChannelUpdatePayload(buf.readString(), buf.readFloat()));


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
