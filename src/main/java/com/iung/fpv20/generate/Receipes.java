package com.iung.fpv20.generate;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.consts.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

import static com.iung.fpv20.consts.ModBlocks.RECEIVER_BLOCK;
import static net.minecraft.block.Blocks.REDSTONE_BLOCK;
import static net.minecraft.item.Items.QUARTZ;

public class Receipes extends FabricRecipeProvider {


    public Receipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder
                .create(RecipeCategory.REDSTONE, RECEIVER_BLOCK)
                .input(REDSTONE_BLOCK)
                .input(QUARTZ)
                .criterion(FabricRecipeProvider.hasItem(REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(REDSTONE_BLOCK))
                .criterion(FabricRecipeProvider.hasItem(QUARTZ), FabricRecipeProvider.conditionsFromItem(QUARTZ))
                .criterion(FabricRecipeProvider.hasItem(RECEIVER_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(RECEIVER_BLOCK))
                .offerTo(exporter);
    }
}
