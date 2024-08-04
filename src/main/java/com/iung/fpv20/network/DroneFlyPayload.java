package com.iung.fpv20.network;

import com.iung.fpv20.Fpv20;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DroneFlyPayload(boolean fly) implements CustomPayload {
    public static final Id<DroneFlyPayload> ID = new Id<>(Identifier.of(Fpv20.MOD_ID, "drone_fly_packet"));
    public static final PacketCodec<PacketByteBuf, DroneFlyPayload> CODEC = PacketCodec.of((value, buf) -> {
        buf.writeBoolean(value.fly);
    }, buf -> new DroneFlyPayload(buf.readBoolean()));


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
