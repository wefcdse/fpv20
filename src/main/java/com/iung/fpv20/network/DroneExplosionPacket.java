package com.iung.fpv20.network;

import com.iung.fpv20.Fpv20;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;

public class DroneExplosionPacket implements FabricPacket {

    public static final PacketType<DroneExplosionPacket> TYPE = PacketType.create(new Identifier(Fpv20.MOD_ID, "drone_explosion_packet"), DroneExplosionPacket::new);

    public float power;
    public boolean stop_fly;
    public Vec3d tele_pos;
    public boolean if_tele;

    private DroneExplosionPacket(PacketByteBuf buf) {
        this.power = buf.readFloat();
        this.stop_fly = buf.readBoolean();
        this.tele_pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.if_tele = buf.readBoolean();
    }

    public DroneExplosionPacket(float power, boolean stop_fly, Vec3d tele_pos, boolean if_tele) {
        this.power = power;
        this.stop_fly = stop_fly;
        this.tele_pos = tele_pos;
        this.if_tele = if_tele;
    }


    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.power);
        buf.writeBoolean(this.stop_fly);
        buf.writeDouble(this.tele_pos.x);
        buf.writeDouble(this.tele_pos.y);
        buf.writeDouble(this.tele_pos.z);
        buf.writeBoolean(this.if_tele);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
