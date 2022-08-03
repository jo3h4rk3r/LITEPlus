package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.mods.PlayerCopy.CopyPlayer;

public class PlayerCopyCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".copy")) {
            assert mc.player != null;
            CopyPlayer();
            ci.cancel();
        }
        if (message.startsWith(".copyplayer")) {
            assert mc.player != null;
            CopyPlayer();
            ci.cancel();
        }

    }
}
