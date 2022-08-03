package liteplus.commands;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static liteplus.mods.Crasher.onCrasher;

public class CrashCommand {
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    //private static String message;



    public static void onCommand(String message, CallbackInfo ci) {
        if (message.startsWith(".crash")) {
            assert mc.player != null;
            System.out.println("FUCK YO USIFGSJUHFGSYUDGSU");
            onCrasher();
            ci.cancel();
        }
        if (message.startsWith(".crash 1")) {
            assert mc.player != null;
            System.out.println("testtttttt");
            ci.cancel();
        }
    }

}
