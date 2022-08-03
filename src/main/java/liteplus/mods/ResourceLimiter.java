package liteplus.mods;

import liteplus.litepluscore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.locks.LockSupport;


public class ResourceLimiter {
    private static long lastRender;
    private static boolean hasRenderedLastFrame = false;

    public static boolean checkForRender() {
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = ((litepluscore.WindowHolder) client).getWindow();

        long currentTime = Util.getMeasuringTimeMs();
        long timeSinceLastRender = currentTime - lastRender;

        boolean isVisible = GLFW.glfwGetWindowAttrib(window.getHandle(), GLFW.GLFW_VISIBLE) != 0;
        boolean shouldReduceFPS = litepluscore.isForcingLowFPS() || !client.isWindowFocused();
        if (!shouldReduceFPS && hasRenderedLastFrame) {
            hasRenderedLastFrame = false;
        }

        boolean shouldRender = isVisible && (!shouldReduceFPS || timeSinceLastRender > 1000);
        if (shouldRender) {
            lastRender = currentTime;
        } else {
            if (!hasRenderedLastFrame) {
                hasRenderedLastFrame = true;
                return true;
            }
            LockSupport.parkNanos("waiting to render", 30_000_000); // 30 ms
            //System.out.println("Waiting to render...");
        }
        return shouldRender;
    }
}
