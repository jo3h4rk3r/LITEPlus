package liteplus.mods;

import liteplus.utils.PlayerCopyEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class PlayerCopy {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean PlayerSpawned;


    public static void CopyPlayer() {
        if (mc.player != null) {

            if (PlayerSpawned) {
                PlayerSpawned = false;
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c removed copied player"));
                PlayerCopyEntity dummy = new PlayerCopyEntity(mc.player);
                dummy.remove(null);
            } else {
                PlayerSpawned = true;
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Copied player"));
                PlayerCopyEntity dummy = new PlayerCopyEntity(mc.player);
                dummy.spawn();
            }

        }
    }
}
