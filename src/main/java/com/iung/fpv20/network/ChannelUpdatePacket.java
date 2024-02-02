package com.iung.fpv20.network;

import com.iung.fpv20.Fpv20;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ChannelUpdatePacket implements FabricPacket {

    public static final PacketType<ChannelUpdatePacket> TYPE = PacketType.create(new Identifier(Fpv20.MOD_ID, "channel_update_packet"), ChannelUpdatePacket::new);

    public String name;
    public float value;

    private ChannelUpdatePacket(PacketByteBuf buf) {
        this.name = buf.readString();
        this.value = buf.readFloat();
    }

    public ChannelUpdatePacket(String names, float value) {
        this.name = names;
        this.value = value;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(this.name);
        buf.writeFloat(this.value);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
