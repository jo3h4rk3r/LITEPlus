package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.mods.NightVision.onNightVision;

public class VisionCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".vision")) {
            assert mc.player != null;
            onNightVision();
            ci.cancel();
        }
        if (message.startsWith(".nightvision")) {
            assert mc.player != null;
            onNightVision();
            ci.cancel();
        }

    }
}
