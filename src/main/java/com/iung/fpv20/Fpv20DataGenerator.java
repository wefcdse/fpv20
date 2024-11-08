package com.iung.fpv20;

import com.iung.fpv20.generate.Receipes;
import com.iung.fpv20.generate.lang.ChineseLangProvider;
import com.iung.fpv20.generate.lang.EnglishLangProvider;
import com.iung.fpv20.generate.model.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class Fpv20DataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

//		FabricDataGenerator.Pack pack = fabricDataGenerator.addProvider();
		fabricDataGenerator.addProvider(EnglishLangProvider::new);
		fabricDataGenerator.addProvider(ChineseLangProvider::new);
		fabricDataGenerator.addProvider(ModModelProvider::new);
		fabricDataGenerator.addProvider(Receipes::new);
	}
}
