package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.mods.DeathExplorer.onDeathExplore;

public class GhostCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".ghost")) {
            assert mc.player != null;
            onDeathExplore();
            ci.cancel();
        }
        if (message.startsWith(".deathexplorer")) {
            assert mc.player != null;
            onDeathExplore();
            ci.cancel();
        }
    }
}
