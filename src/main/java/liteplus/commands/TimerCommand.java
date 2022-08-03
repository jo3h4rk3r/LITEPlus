package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.mods.Overclock.onOverClock;

public class TimerCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".timer")) {
            assert mc.player != null;
            onOverClock();
            ci.cancel();
        }
        if (message.startsWith(".overclock")) {
            assert mc.player != null;
            onOverClock();
            ci.cancel();
        }

    }
}
