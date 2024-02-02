package com.iung.fpv20.network;

import com.iung.fpv20.Fpv20;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SetReceiverPacket implements FabricPacket {

    public static final PacketType<SetReceiverPacket> TYPE = PacketType.create(new Identifier(Fpv20.MOD_ID, "set_receiver_packet"), SetReceiverPacket::new);

    public String channel_name;
    public boolean neg;

    public BlockPos pos;

    private SetReceiverPacket(PacketByteBuf buf) {
        this.channel_name = buf.readString();
        this.neg = buf.readBoolean();
        this.pos = buf.readBlockPos();
    }

    public SetReceiverPacket(String names, boolean neg,BlockPos pos) {
        this.channel_name = names;
        this.neg = neg;
        this.pos = pos;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(this.channel_name);
        buf.writeBoolean(this.neg);
        buf.writeBlockPos(this.pos);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
