package com.iung.fpv20.network;

import com.iung.fpv20.Fpv20;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;
//import net.fabricmc.fabric.api.networking.v1.FabricPacket;
//import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.nio.ByteBuffer;

public class DroneFlyPacket {

    //    public static final PacketType<DroneFlyPacket> TYPE = PacketType.create(new Identifier(Fpv20.MOD_ID, "drone_fly_packet"), DroneFlyPacket::new);
    public static final Identifier ID = new Identifier(Fpv20.MOD_ID, "drone_fly_packet");
    public boolean fly;

    private DroneFlyPacket(PacketByteBuf buf) {
        this.fly = buf.readBoolean();

    }

    public DroneFlyPacket(boolean fly) {
        this.fly = fly;
    }


    //    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.fly);
    }

    public PacketByteBuf asPacket() {
        PacketByteBuf pkt = new PacketByteBuf(Unpooled.buffer(32));
        this.write(pkt);
        return pkt;
    }
//    @Override
//    public PacketType<?> getType() {
//        return TYPE;
//    }
}
