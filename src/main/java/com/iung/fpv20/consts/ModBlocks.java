package com.iung.fpv20.consts;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.blocks.ReceiverBlock;
import com.iung.fpv20.blocks.ReceiverBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Identifier RECEIVER_BLOCK_ID = new Identifier(Fpv20.MOD_ID, "receiver_block");
    public static final Block RECEIVER_BLOCK = registerBlock(RECEIVER_BLOCK_ID, new ReceiverBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_BLOCK)));

    public static final BlockEntityType<ReceiverBlockEntity> RECEIVER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Fpv20.MOD_ID, "receiver_block_entity"),
            FabricBlockEntityTypeBuilder.create(ReceiverBlockEntity::new, RECEIVER_BLOCK).build()
    );


    private static Block registerBlock(Identifier id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(Registries.BLOCK, id, block);
    }

    private static Item registerBlockItem(Identifier id, Block block) {
        return Registry.register(Registries.ITEM, id,
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Fpv20.LOGGER.info("Registering Modblocks for" + Fpv20.MOD_ID);
    }
}
