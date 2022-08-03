package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.mods.Invisible.onInvisible;

public class VanishCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".vanish")) {
            assert mc.player != null;
            onInvisible();
            ci.cancel();
        }
        if (message.startsWith(".invisible")) {
            assert mc.player != null;
            onInvisible();
            ci.cancel();
        }

    }
}
