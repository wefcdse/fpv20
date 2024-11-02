package com.iung.fpv20.generate;

import com.iung.fpv20.Fpv20;
import com.iung.fpv20.consts.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.ItemCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.iung.fpv20.consts.ModBlocks.RECEIVER_BLOCK;
import static net.minecraft.block.Blocks.REDSTONE_BLOCK;
import static net.minecraft.item.Items.QUARTZ;

public class Receipes extends FabricRecipeProvider {
    public Receipes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RG(registryLookup, exporter);
    }

    @Override
    public String getName() {
        return "fpv20_rcp";
    }

    public static class RG extends RecipeGenerator {

        protected RG(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
            super(registries, exporter);
        }

        @Override
        public void generate() {
//            this.createShapeless(RecipeCategory.REDSTONE, RECEIVER_BLOCK).offerTo(exporter);
//            ShapelessRecipeJsonBuilder
//                    .create(Registries.ITEM, RecipeCategory.REDSTONE, RECEIVER_BLOCK.asItem())
//                    .input(REDSTONE_BLOCK)
//                    .input(QUARTZ)
//                    .criterion("recipe_condition",new AdvancementCriterion<>(ItemCriterion))
////                .criterion(FabricRecipeProvider.hasItem(REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(REDSTONE_BLOCK))
////                .criterion(FabricRecipeProvider.hasItem(QUARTZ), FabricRecipeProvider.conditionsFromItem(QUARTZ))
////                .criterion(FabricRecipeProvider.hasItem(RECEIVER_BLOCK),
////                        FabricRecipeProvider.conditionsFromItem(RECEIVER_BLOCK))
//                    .offerTo(exporter);
        }
    }


//    private MyRecipeGenerator(FabricDataOutput generator) {
//        super(generator);
//    }
//
//    @Override
//    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
//        // ...
//    }
//    public Receipes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
//        super(output, registryLookup);
//    }
//
//    @Override
//    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
//        return null;
//    }
//
//    @Override
//    public void generate(RecipeExporter exporter) {
//        ShapelessRecipeJsonBuilder
//                .create(RecipeCategory.REDSTONE, RECEIVER_BLOCK)
//                .input(REDSTONE_BLOCK)
//                .input(QUARTZ)
//                .criterion(FabricRecipeProvider.hasItem(REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(REDSTONE_BLOCK))
//                .criterion(FabricRecipeProvider.hasItem(QUARTZ), FabricRecipeProvider.conditionsFromItem(QUARTZ))
//                .criterion(FabricRecipeProvider.hasItem(RECEIVER_BLOCK),
//                        FabricRecipeProvider.conditionsFromItem(RECEIVER_BLOCK))
//                .offerTo(exporter);
//    }
//
//    @Override
//    public String getName() {
//        return "";
//    }

//    @Override
//    public void generate(Consumer<RecipeJsonProvider> exporter) {
//        ShapelessRecipeJsonBuilder
//                .create(RecipeCategory.REDSTONE, RECEIVER_BLOCK)
//                .input(REDSTONE_BLOCK)
//                .input(QUARTZ)
//                .criterion(FabricRecipeProvider.hasItem(REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(REDSTONE_BLOCK))
//                .criterion(FabricRecipeProvider.hasItem(QUARTZ), FabricRecipeProvider.conditionsFromItem(QUARTZ))
//                .criterion(FabricRecipeProvider.hasItem(RECEIVER_BLOCK),
//                        FabricRecipeProvider.conditionsFromItem(RECEIVER_BLOCK))
//                .offerTo(exporter);
//    }
}
