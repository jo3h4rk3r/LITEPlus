package liteplus;

//import liteplus.hud.HudInfoRenderer;
//import liteplus.eventbus.liteEventBus;

import liteplus.hud.screenHud;
import liteplus.screens.HudOptions;
import liteplus.utils.KeyBindingHandler;
import liteplus.utils.file.FileMang;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

//import net.minecraft.client.options.KeyBinding;

public class litepluscore implements ModInitializer {
    private static litepluscore INSTANCE = null;
    public static final String MOD_ID = "litepluscore";
    public static final String VERSION = "v1.1";
    private static boolean isForcingLowFPS = false;
    public static boolean isChunksLoading = true;
    public static boolean SmartTrees = true;
    public static boolean ShowHUD = true;
    public static boolean ShowHUDStats = false;
    //public static boolean playerCrasher = false;

    public static boolean isForcingLowFPS() {return isForcingLowFPS;}
    public static boolean ShowHUD() {
        return ShowHUD;
    }
    public static boolean ShowHUDStats() { return ShowHUDStats; }

    private static final KeyBinding ShowHUDPanel = new KeyBinding("Toggle hud", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_O, "LITEPLUS - HUD");
    private static final KeyBinding ShowHUDPanelStats = new KeyBinding("Toggle hud stats", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_I, "LITEPLUS - HUD");
    private static final KeyBinding LimitFPS = new KeyBinding("Toggle program haulter", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_J, "LITEPLUS - LIMITER");
    private static final KeyBinding binding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle Gui Menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "LITEPLUS - GUI"));
    //private static final KeyBinding binding4 = KeyBindingHelper.registerKeyBinding(new KeyBinding("Player crasher", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "LITEPLUS - MODS"));
    //private static final KeyBinding binding3 = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle chunk loading", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "LITEPLUS - LIMITER"));
    //private static final KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding("Death Explorer/ghost mode", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "LITEPLUS - MODS"));
    //private static final KeyBinding stickyBinding = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("Player crasher", GLFW.GLFW_KEY_H, "LITEPLUS - MODS", () -> true));

    @Override
    public void onInitialize() {
        System.out.println("LITEPLUS INIT");
        FileMang.init();
        KeyBindingHelper.registerKeyBinding(LimitFPS);
        KeyBindingHelper.registerKeyBinding(ShowHUDPanel);
        KeyBindingHelper.registerKeyBinding(ShowHUDPanelStats);
        ClientTickEvents.END_CLIENT_TICK.register(new KeyBindingHandler(LimitFPS, () -> isForcingLowFPS = !isForcingLowFPS));
        ClientTickEvents.END_CLIENT_TICK.register(new KeyBindingHandler(ShowHUDPanel, () -> ShowHUD = !ShowHUD));
        ClientTickEvents.END_CLIENT_TICK.register(new KeyBindingHandler(ShowHUDPanelStats, () -> ShowHUDStats = !ShowHUDStats));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Integer guiScale = client.options.getGuiScale().getValue();
            //     -=- GUI OPTIONS Keybinding -=-
            while (binding2.wasPressed()) {
                if (guiScale == 3) {
                    client.setScreen(new HudOptions(false));
                } else {
                    //client.setScreen(new HudOptionsgui2(false));
                    client.setScreen(new HudOptions(false));
                    assert client.player != null;
                    client.player.sendMessage(Text.literal("§cERROR: §eSwitch gui scale to 3"));
                }
            }
        });
        HudRenderCallback.EVENT.register(new screenHud());
    }

    public interface WindowHolder {
        Window getWindow();
    }


}