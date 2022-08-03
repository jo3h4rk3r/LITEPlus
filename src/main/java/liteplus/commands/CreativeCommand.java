package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.mods.Creative.onCreative;

public class CreativeCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".creative")) {
            assert mc.player != null;
            onCreative();
            ci.cancel();
        }
    }
}
