package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class LimiterCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".limit")) {
            assert mc.player != null;
            //onDeathExplore();
            ci.cancel();
        }
    }

}
