package com.iung.fpv20.blocks;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.consts.ModBlocks;
import com.iung.fpv20.globals.AllChannels;
import com.iung.fpv20.screen_handler.ReceiverBlockHandler;
import com.iung.fpv20.utils.FastMath;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ReceiverBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ExtendedScreenHandlerFactory {

    public String channel;
    public boolean neg;

    public int last_value = 0;

    public ReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.RECEIVER_BLOCK_ENTITY, pos, state);
        this.channel = "CH0";
        this.neg = false;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        // Save the current value of the number to the tag
        nbt.putString("fpv20_channel", channel);
        nbt.putBoolean("fpv20_neg", neg);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        channel = nbt.getString("fpv20_channel");
        neg = nbt.getBoolean("fpv20_neg");
    }

    public static void tick(World world, BlockPos pos, BlockState state, ReceiverBlockEntity be) {
        float value = 0;
        AllChannels.ChannelInfo i = AllChannels.ALL_CHANNELS.get(be.channel);
        if (i != null) {
            value = i.value;
        }
        if (be.neg) {
            value = -value;
        }
        int new_value = FastMath.clamp(Math.round(value * 15), 0, 15);

        int last = be.last_value;

        be.last_value = new_value;

        if (new_value != last) {
//            Fpv20.LOGGER.info("{} {} {}", new_value, be.last_value, value);
            world.updateNeighborsAlways(pos, state.getBlock());
        }

        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    //////////////////////////
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        // 因为我们的类实现 Inventory，所以将*这个*提供给 ScreenHandler
        // 一开始只有服务器拥有物品栏，然后在 ScreenHandler 中同步给客户端
        return new ReceiverBlockHandler(syncId, pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
        // 对于1.19之前的版本，请使用：
        // return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }


    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {

        buf.writeBlockPos(pos);
    }
}
