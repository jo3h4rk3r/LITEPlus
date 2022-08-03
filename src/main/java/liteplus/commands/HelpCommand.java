package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HelpCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".help")) {
            assert mc.player != null;
            mc.player.sendMessage(Text.literal(""));
            mc.player.sendMessage(Text.literal("Liteplus v1.1 help:"));
            mc.player.sendMessage(Text.literal(""));
            mc.player.sendMessage(Text.literal("1) .commands for a list of commands"));
            mc.player.sendMessage(Text.literal("2) press u for gui"));
            mc.player.sendMessage(Text.literal(""));
            ci.cancel();
        }
    }
}
