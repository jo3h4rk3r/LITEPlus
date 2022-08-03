package liteplus.mixin;

//import liteplus.titlescreen.MainMenu;
import liteplus.screens.MainMenu;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TitleScreen.class})
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }


    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {

        MinecraftClient.getInstance().setScreen(new MainMenu());
        MainMenu.customTitleScreen = !MainMenu.customTitleScreen;
        assert client != null;

    }


}


