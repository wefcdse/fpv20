package com.iung.fpv20.consts;

import com.iung.fpv20.Fpv20;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup RUBY_GROUP = Fpv20.config.client_only ? null : Registry.register(Registries.ITEM_GROUP,
            new Identifier(Fpv20.MOD_ID, "ruby"),
            FabricItemGroup.builder().displayName(Text.literal("FPV20"))
                    .icon(() -> new ItemStack(ModBlocks.RECEIVER_BLOCK.asItem())).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.RECEIVER_BLOCK);

                    }).build());

    public static void registerItemGroups() {
        Fpv20.LOGGER.info("Registering Item Group for " + Fpv20.MOD_ID);
    }

}
