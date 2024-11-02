package com.iung.fpv20.consts;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.blocks.ReceiverBlock;
import com.iung.fpv20.blocks.ReceiverBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
//import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Identifier RECEIVER_BLOCK_ID = Identifier.of(Fpv20.MOD_ID, "receiver_block");
    public static final Block RECEIVER_BLOCK = registerBlock(RECEIVER_BLOCK_ID,
            new ReceiverBlock(
                    AbstractBlock.Settings
                            .copy(Blocks.REDSTONE_BLOCK)
                            .registryKey(RegistryKey.of(RegistryKeys.BLOCK, RECEIVER_BLOCK_ID))
            ));

    public static final BlockEntityType<ReceiverBlockEntity> RECEIVER_BLOCK_ENTITY = Fpv20.config.client_only ? null : Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(Fpv20.MOD_ID, "receiver_block_entity"),
            FabricBlockEntityTypeBuilder.create(ReceiverBlockEntity::new, RECEIVER_BLOCK).build()
    );


    private static Block registerBlock(Identifier id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(Registries.BLOCK, id, block);
    }

    private static Item registerBlockItem(Identifier id, Block block) {
        return Registry.register(Registries.ITEM, id,
                new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM,RECEIVER_BLOCK_ID))));
    }

    public static void registerModBlocks() {
        Fpv20.LOGGER.info("Registering Modblocks for" + Fpv20.MOD_ID);
    }
}
