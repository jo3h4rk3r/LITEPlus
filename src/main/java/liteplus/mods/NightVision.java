package liteplus.mods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;

public class NightVision {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean NightVision = false;

    public static void onNightVision() {
        if (mc.player != null) {
            if (NightVision) {
                NightVision = false;
                mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Night vision disabled"));
            } else {
                NightVision = true;
                mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 500, 0));
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Night vision enabled"));
            }
        }
    }
}
