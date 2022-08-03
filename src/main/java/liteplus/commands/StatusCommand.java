package liteplus.commands;

import liteplus.litepluscore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class StatusCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".status")) {
            assert mc.player != null;
            PlayerListEntry playerEntry = mc.player.networkHandler.getPlayerListEntry(mc.player.getGameProfile().getId());
            assert playerEntry != null;
            mc.player.sendMessage(Text.literal(""));
            mc.player.sendMessage(Text.literal("Liteplus " + litepluscore.VERSION + " client status:"));
            mc.player.sendMessage(Text.literal(""));
            mc.player.sendMessage(Text.literal("Playername: " + mc.player.getName().getString()));
            mc.player.sendMessage(Text.literal("Gamemode: " + playerEntry.getGameMode()));
            mc.player.sendMessage(Text.literal("Ping: " + playerEntry.getLatency()));
            mc.player.sendMessage(Text.literal("Ram usage: NULL"));
            mc.player.sendMessage(Text.literal("Cpu usage: NULL"));
            ci.cancel();
        }
    }
}
