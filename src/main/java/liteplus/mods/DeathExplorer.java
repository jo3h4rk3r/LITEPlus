package liteplus.mods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;


public class DeathExplorer {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean ExploreDeath = false;
    public static boolean deathDisconnect = false;
    private static boolean isDead = false;
    private static int deathTimer = 0;

    public static void onDeathExplore() {
        if (!(mc.player == null)) {
            if (ExploreDeath) {
                ExploreDeath = false;
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Death explorer disabled"));
            } else {
                ExploreDeath = true;
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Death explorer enabled"));
            }
        }
    }

    public static void onTick() {
        if (!(mc.player == null)) {
            //            -=-   Explore death -=-=-
            if (ExploreDeath) {
                if (mc.player.isDead()) {
                    isDead = true;
                    mc.player.getAbilities().flying = true;
                    mc.player.getAbilities().allowFlying = true;
                    mc.player.setHealth(20f);
                    mc.setScreen(null);
                    System.out.println("You died lol");
                    deathDisconnect = true;
                }
            } else if (isDead) {
                mc.player.setHealth(0);
                isDead = false;
                mc.player.getAbilities().flying = false;
                mc.player.getAbilities().allowFlying = false;
                deathDisconnect = false;
                deathTimer = 0;
            }
            //          -=- Disconnect on death -=-
            if (deathDisconnect) {
                deathTimer++;
                if (deathTimer == 50) {
                    assert mc.world != null;
                    mc.player.clientWorld.disconnect();
                    mc.player.networkHandler.onDisconnected(Text.literal("You are now dead. Join any server you choose :)"));
                    deathDisconnect = false;
                }
            }
        }
    }

    // inspired by BleachHack
}
