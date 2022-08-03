package liteplus.mods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Overclock {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean TimerActive = false;


    public static void onOverClock() {
        if (mc.player != null) {
            if (TimerActive) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Timer disabled"));
                TimerActive = false;
            } else {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Timer enabled"));
                TimerActive = true;
            }
        }
    }


     // inspired by BleachHack

     // See MixinRenderTickCounter for code

}
