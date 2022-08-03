package liteplus.mods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class Creative {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public static void onCreative() {
        assert mc.interactionManager != null;
        if (!(mc.player == null)) {
            if (mc.interactionManager.getCurrentGameMode().isCreative()) {
                mc.player.setClientPermissionLevel(4);
                mc.interactionManager.setGameMode(GameMode.SURVIVAL);
                mc.player.setInvulnerable(false);
                mc.getDataFixer();
                mc.player.getAbilities().flying = false;
                mc.player.getAbilities().allowFlying = false;
                mc.player.getAbilities().creativeMode = false;
                mc.player.getAbilities().invulnerable = false;
                mc.player.sendAbilitiesUpdate();
                mc.player.sendMessage(Text.literal("LITEPLUS: Your gamemode has been updated"));
            } else {
                mc.player.setClientPermissionLevel(4);
                mc.interactionManager.setGameMode(GameMode.CREATIVE);
                mc.player.setInvulnerable(true);
                mc.getDataFixer();
                mc.player.getAbilities().allowModifyWorld = true;
                mc.player.getAbilities().flying = true;
                mc.player.getAbilities().allowFlying = true;
                mc.player.getAbilities().creativeMode = true;
                mc.player.getAbilities().invulnerable = true;
                mc.player.sendAbilitiesUpdate();
                mc.player.sendMessage(Text.literal("LITEPLUS: Your gamemode has been updated"));
            }
        }
    }
}
