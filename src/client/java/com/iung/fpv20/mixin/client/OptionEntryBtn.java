package com.iung.fpv20.mixin.client;


import com.iung.fpv20.consts.Texts;
import com.iung.fpv20.gui.OptionsMainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsOptionsScreen.class)
public class OptionEntryBtn extends Screen {
    protected OptionEntryBtn(Text title) {
        super(title);
    }

    @Unique
    private static final int WIDTH = 30;

    @Inject(at = @At("HEAD"), method = "init()V")

    public void init(CallbackInfo ci) {
        int i = this.width / 2 - 155 - 10 - WIDTH;
        int k = this.height / 6 - 12;
        this.addDrawableChild(ButtonWidget.builder(Texts.BTN_OPTION_ENTRY, (a) -> {
            System.out.println("Btn1");
            if (this.client != null) {
                this.client.setScreen(new OptionsMainScreen(this));
            }
        }).dimensions(i, k, WIDTH, 20).build());

    }
}
