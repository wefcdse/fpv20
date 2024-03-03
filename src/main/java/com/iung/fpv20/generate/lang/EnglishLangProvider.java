package com.iung.fpv20.generate.lang;

import com.iung.fpv20.consts.ModBlocks;
import com.iung.fpv20.consts.TranslateKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EnglishLangProvider extends FabricLanguageProvider {
    public EnglishLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput,"en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.RECEIVER_BLOCK,"receiver block");


        translationBuilder.add(TranslateKeys.BTN_OPTION_ENTRY,"FPV");
        translationBuilder.add(TranslateKeys.TITLE_MAIN_OPTION_SCREEN,"FPV options");
        translationBuilder.add(TranslateKeys.BTN_SELECT_CONTROLLER,"select controller");
        translationBuilder.add(TranslateKeys.BTN_SELECT,"select");
        translationBuilder.add(TranslateKeys.RATE_1,"rate:%s");
        translationBuilder.add(TranslateKeys.BTN_CALIBRATE,"calibrate");

        translationBuilder.add(TranslateKeys.BTN_CALIBRATE_CENTER,"calibrate stick center");
        translationBuilder.add(TranslateKeys.BTN_CALIBRATE_RANGE,"calibrate stick range");

        translationBuilder.add(TranslateKeys.BTN_START,"start");
        translationBuilder.add(TranslateKeys.BTN_END,"end");

        translationBuilder.add(TranslateKeys.BTN_OPTION,"option");


        translationBuilder.add(TranslateKeys.BTN_CALIBRATE_MAX_MIN,"method:max min");
        translationBuilder.add(TranslateKeys.BTN_CALIBRATE_MAX_MID_MIN,"method:max zero min");
        translationBuilder.add(TranslateKeys.BTN_CALIBRATE_RAW,"method:raw");

        translationBuilder.add(TranslateKeys.TEXT_SET_CHANNEL_NAME,"set channel name");

        translationBuilder.add(TranslateKeys.BTN_SHOW_INPUT,"show input");
        translationBuilder.add(TranslateKeys.BTN_BTN_CONFIG,"button/switch config");

        translationBuilder.add(TranslateKeys.BTN_REVERSED,"reversed");
        translationBuilder.add(TranslateKeys.BTN_NO_REVERSED,"no reverse");
    }
}
