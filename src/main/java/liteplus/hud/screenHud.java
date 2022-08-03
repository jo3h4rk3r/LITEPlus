package liteplus.hud;


import liteplus.litepluscore;
import liteplus.utils.FabricReflect;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

import static liteplus.litepluscore.VERSION;

//import static liteplus.litepluscore.IsShowingHudMenu;

public class screenHud implements HudRenderCallback {
    public static long lastPacket = 0;
    public static long prevTime = 0;
    public static double tps = 20;
    private Text tpsText = Text.empty();
    //  public long prevTime = 0;
   // public double tps = 20;
   // private long lastPacket = 0;
    int arrayCount = 0;
    protected MinecraftClient mc = MinecraftClient.getInstance();
    public int counter = 0;







    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
        /*runs code 20 times a second, prolly doo doo asf*/
        if (litepluscore.ShowHUD()) {
            /* Checks if enabled */
            if (mc.getCurrentServerEntry() == null) {
                /*Checks if singleplayer or server*/
                assert mc.player != null;
                Vec3d vec = mc.player.getPos();
                BlockPos pos = mc.player.getBlockPos();
                assert mc.world != null;
                boolean nether = mc.world.getRegistryKey().getValue().getPath().contains("nether");
                BlockPos pos2 = nether ? new BlockPos(vec.getX() * 8, vec.getY(), vec.getZ() * 8)
                        : new BlockPos(vec.getX() / 8, vec.getY(), vec.getZ() / 8);
                int FPSCount = (int) FabricReflect.getFieldValue(MinecraftClient.getInstance(), "field_1738", "currentFps");
                /*Draws Text*/
                if (litepluscore.ShowHUDStats()) {
                    mc.textRenderer.drawWithShadow(matrices, "§e§lLITE§9§lPLUS §f§l" + VERSION + " §f" + "Singleplayer", 10, 10, 0, false);
                    mc.textRenderer.drawWithShadow(matrices, "§f§lFPS:§a " + FPSCount, 10, 30, 0, false);
                    mc.textRenderer.drawWithShadow(matrices, "§9POS: " + (nether ? "\u00a74" : "\u00a7b") + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                            + " \u00a77[" + (nether ? "\u00a7b" : "\u00a74") + pos2.getX() + " " + pos2.getY() + " " + pos2.getZ() + "\u00a77]", 10, 50, 0, false);
                } else {
                    mc.textRenderer.drawWithShadow(matrices, "§e§lLITE§9§lPLUS §f§l" + VERSION, 10, 10, 0, false);
                    mc.textRenderer.drawWithShadow(matrices, "§f§lFPS:§a " + FPSCount, 10, 30, 0, false);
                }
            } else {




                    int FPSCount = (int) FabricReflect.getFieldValue(MinecraftClient.getInstance(), "field_1738", "currentFps");
                    String ServerVersion = Objects.requireNonNull(mc.getCurrentServerEntry()).version.getString();




                    if (litepluscore.ShowHUDStats()) {
                        String ServerIP = mc.getCurrentServerEntry() == null ? "Singleplayer" : mc.getCurrentServerEntry().address;

                        assert mc.world != null;
                        boolean nether = mc.world.getRegistryKey().getValue().getPath().contains("nether");
                        assert mc.player != null;
                        BlockPos pos = mc.player.getBlockPos();
                        Vec3d vec = mc.player.getPos();
                        BlockPos pos2 = nether ? new BlockPos(vec.getX() * 8, vec.getY(), vec.getZ() * 8)
                                : new BlockPos(vec.getX() / 8, vec.getY(), vec.getZ() / 8);
                        PlayerListEntry playerEntry = mc.player.networkHandler.getPlayerListEntry(mc.player.getGameProfile().getId());
                        int ping = playerEntry == null ? 0 : playerEntry.getLatency();
                        String playersOnline = mc.getCurrentServerEntry().playerCountLabel.getString();
                        //long playersOnline = Arrays.stream(mc.world.getPlayers().toArray()).count();
                        //Text maxPlayersOnline = mc.getCurrentServerEntry().playerCountLabel;
                       // int maxonline2 = mc.player.getServer().getMaxPlayerCount();





                        int time = (int) (System.currentTimeMillis() - lastPacket);
                        String suffix = time >= 7500 ? "...." : time >= 5000 ? "..." : time >= 2500 ? ".." : time >= 1200 ? ".." : "";

                        tpsText = Text.literal("")
                                .append(colorText(Double.toString(tps), (float) MathHelper.clamp(tps - 2, 0, 16) / 48))
                                .append(suffix);

                        mc.textRenderer.drawWithShadow(matrices, "§e§lLITE§9§lPLUS §f§l" + VERSION + " §f" + ServerVersion, 10, 10, 0, false);
                        mc.textRenderer.drawWithShadow(matrices, "§f§lFPS:§a " + FPSCount, 10, 30, 0, false);
                        mc.textRenderer.drawWithShadow(matrices, "§9Online§8:§b " + playersOnline, 10, 50, 0, false);
                        mc.textRenderer.drawWithShadow(matrices, "§9Ping§8:§b " + ping + "ms", 10, 60, 0, false);
                        mc.textRenderer.drawWithShadow(matrices, "§9TPS§8:§b " + tpsText.getString(), 10, 70, 0, false);
                        mc.textRenderer.drawWithShadow(matrices, "§9IP§8:§b " + ServerIP, 10, 80, 0, false);
                        mc.textRenderer.drawWithShadow(matrices, "§9POS§8: " + (nether ? "\u00a74" : "\u00a7b") + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                                + " \u00a77[" + (nether ? "\u00a7b" : "\u00a74") + pos2.getX() + " " + pos2.getY() + " " + pos2.getZ() + "\u00a77]", 10, 90, 0, false);
                    } else {
                        mc.textRenderer.drawWithShadow(matrices, "§e§lLITE§9§lPLUS §f§l" + VERSION, 10, 10, 0, false);
                        mc.textRenderer.drawWithShadow(matrices, "§f§lFPS:§a " + FPSCount, 10, 30, 0, false);
                    }
            }
        }
        if (litepluscore.isForcingLowFPS()) {
            mc.textRenderer.drawWithShadow(matrices, "§chalting program resources!",10, 110, 0, false);
        }
    }

    private static Text colorText(String text, float hue) {
        return Text.literal(text).styled(s -> s.withColor(MathHelper.hsvToRgb(hue, 10f, 10f)));
    }

  /*  @liteSubscribe
    public void readPacket(EventPacket.Read event) {
        lastPacket = System.currentTimeMillis();
        System.out.println("Deleteing packs");

        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket) {
            long time = System.currentTimeMillis();
            System.out.println("Deleteing packs");
            long timeOffset = Math.abs(1000 - (time - prevTime)) + 1000;
            tps = Math.round(MathHelper.clamp(20 / (timeOffset / 1000d), 0, 20) * 100d) / 100d;
            prevTime = time;
        }
    }

   */


}
