package liteplus.mods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class Flight {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean FlightEnabled = false;


    public static void onFlight() {
        if (mc.player != null) {
            if (FlightEnabled) {
                FlightEnabled = false;
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Flight disabled"));
            } else {
                FlightEnabled = true;
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Flight enabled"));
            }
        }
    }

    public static void onTick() {
        if (mc.player != null) {
            if (FlightEnabled) {
                double speed = 0.1;

                if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), InputUtil.fromTranslationKey(mc.options.jumpKey.getBoundKeyTranslationKey()).getCode())) {
                    mc.player.jump();
                } else {
                    if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), InputUtil.fromTranslationKey(mc.options.jumpKey.getBoundKeyTranslationKey()).getCode())) {
                        mc.player.updatePosition(mc.player.getX(), mc.player.getY() - speed / 10f, mc.player.getZ());
                    }
                }
                Vec3d forward = new Vec3d(0, 0, speed).rotateY(-(float) Math.toRadians(mc.player.getYaw()));
                Vec3d strafe = forward.rotateY((float) Math.toRadians(90));
                if (mc.options.leftKey.isPressed())
                    mc.player.setVelocity(mc.player.getVelocity().add(strafe.x, 0, strafe.z));
                if (mc.options.rightKey.isPressed())
                    mc.player.setVelocity(mc.player.getVelocity().add(-strafe.x, 0, -strafe.z));

                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));

                if (mc.player.isFallFlying()) {
                    Vec3d vec3d = new Vec3d(0, 0, 0).rotateY(-(float) Math.toRadians(mc.player.getYaw()));

                    if (mc.options.jumpKey.isPressed()) vec3d = vec3d.add(0, 1, 0).rotateY(-(float) Math.toRadians(mc.player.getYaw()));;
                    if (mc.options.sneakKey.isPressed()) vec3d = vec3d.add(0, -1, 0).rotateY(-(float) Math.toRadians(mc.player.getYaw()));;
                    if (mc.options.forwardKey.isPressed()) vec3d = vec3d.add(0, 0, 1).rotateY(-(float) Math.toRadians(mc.player.getYaw()));;
                    if (mc.options.backKey.isPressed()) vec3d = vec3d.add(0, 0, -1).rotateY(-(float) Math.toRadians(mc.player.getYaw()));;
                    if (mc.options.rightKey.isPressed()) vec3d = vec3d.add(-1, 0, 0).rotateY(-(float) Math.toRadians(mc.player.getYaw()));;
                    if (mc.options.leftKey.isPressed()) vec3d = vec3d.add(1, 0, 0).rotateY(-(float) Math.toRadians(mc.player.getYaw()));;

                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                            mc.player.getX() + vec3d.x, mc.player.getY() - 0.01, mc.player.getZ() + vec3d.z, false));

                    mc.player.setVelocity(vec3d.x, vec3d.y, vec3d.z);
                }
            }
        }
    }
}
