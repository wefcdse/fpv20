package com.iung.fpv20.network;

import com.iung.fpv20.Fpv20;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class DroneFlyPacket implements FabricPacket {

    public static final PacketType<DroneFlyPacket> TYPE = PacketType.create(new Identifier(Fpv20.MOD_ID, "drone_fly_packet"), DroneFlyPacket::new);

    public boolean fly;

    private DroneFlyPacket(PacketByteBuf buf) {
        this.fly = buf.readBoolean();

    }

    public DroneFlyPacket(boolean fly) {
        this.fly = fly;
    }


    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.fly);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
