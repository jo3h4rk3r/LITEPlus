package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.screens.HudOptions.SkyColorSet;

public class SkyColorCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static void onCommand(String message, CallbackInfo ci) {
        if (mc.player != null) {
            if (message.startsWith(".skycolor")) {
                mc.player.sendMessage(Text.literal("§eUsage§7:§f .skycolor §3color"));
                ci.cancel();
            }
            if (message.startsWith(".skycolor default")) {
                mc.player.sendMessage(Text.literal("§e§lLITE§9§lPLUS§7:§a Sky color set to §3default"));
                SkyColorSet = 0;
            }
            if (message.startsWith(".skycolor blue")) {
                mc.player.sendMessage(Text.literal("§e§lLITE§9§lPLUS§7:§a Sky color set to §9Blue"));
                SkyColorSet = 1;
            }
            if (message.startsWith(".skycolor yellow")) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Sky color set to §eYellow"));
                SkyColorSet = 2;
            }
            if (message.startsWith(".skycolor green")) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Sky color set to §aGreen"));
                SkyColorSet = 3;
            }
            if (message.startsWith(".skycolor orange")) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Sky color set to §6Orange"));
                SkyColorSet = 4;
            }
            if (message.startsWith(".skycolor red")) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Sky color set to §4Red"));
                SkyColorSet = 5;
            }
            if (message.startsWith(".skycolor magenta")) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Sky color set to §5Magenta"));
                SkyColorSet = 6;
            }
            if (message.startsWith(".skycolor pink")) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Sky color set to §dPink"));
                SkyColorSet = 7;
            }
            if (message.startsWith(".skycolor black")) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Sky color set to §0Black"));
                SkyColorSet = 8;
            }
        }
    }
}
