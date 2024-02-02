package com.iung.fpv20.generate.model;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.consts.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RECEIVER_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModBlocks.RECEIVER_BLOCK.asItem(), registerItemBlockModel(ModBlocks.RECEIVER_BLOCK));
    }

    private static Model registerItemBlockModel(Block parent, TextureKey... requiredTextureKeys) {
        String name = ModelIds.getBlockModelId(parent).getPath();
        return new Model(Optional.of(new Identifier(Fpv20.MOD_ID, name)), Optional.empty(), requiredTextureKeys);
    }
}
