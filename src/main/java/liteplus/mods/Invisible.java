package liteplus.mods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Invisible {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean playerInvisible = false;
    public static void onInvisible() {
        if (!(mc.player == null)) {
            if (playerInvisible) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c You are no longer invisible"));
                playerInvisible = false;
            } else {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a You are now invisible"));
                playerInvisible = true;
            }
        }
    }

    public static void onTick() {
        if (mc.player != null) {
            if (playerInvisible) {
                mc.player.setInvisible(true);
            } else {
                if (mc.player.isInvisible()) {
                    mc.player.setInvisible(false);
                }
            }
        }
    }
}
