package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CommandList {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".commands")) {
            assert mc.player != null;
            mc.player.sendMessage(Text.literal(""));
            mc.player.sendMessage(Text.literal("Commands:"));
            mc.player.sendMessage(Text.literal(""));
            mc.player.sendMessage(Text.literal("1) .fly / .flight"));
            mc.player.sendMessage(Text.literal("2) .vision / .nightvision"));
            mc.player.sendMessage(Text.literal("3) .vanish / .invisible"));
            mc.player.sendMessage(Text.literal("4) .ghost / .exploredeath"));
            mc.player.sendMessage(Text.literal("5) .creative"));
            mc.player.sendMessage(Text.literal("6) .crash / .crash ammount"));
            mc.player.sendMessage(Text.literal("7) .timer / .overclock"));
            mc.player.sendMessage(Text.literal("8) .limiter"));
            mc.player.sendMessage(Text.literal("9) .copy / .copyplayer"));
            mc.player.sendMessage(Text.literal("10) .skycolor color"));
            mc.player.sendMessage(Text.literal(""));
            ci.cancel();
        }
    }
}
