package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.mods.Flight.onFlight;

public class FlightCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".fly")) {
            assert mc.player != null;
            onFlight();
            ci.cancel();
        }
        if (message.startsWith(".flight")) {
            assert mc.player != null;
            onFlight();
            ci.cancel();
        }
    }
}
