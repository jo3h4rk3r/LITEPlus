package liteplus.screens;

//import liteplus.eventbus.liteSubscribe;
import liteplus.window.AbstractWindowScreen;
import liteplus.window.Window;
import liteplus.window.WindowButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import static liteplus.litepluscore.ShowHUD;
import static liteplus.litepluscore.ShowHUDStats;

@Environment(EnvType.CLIENT)
public class HudSettings extends AbstractWindowScreen {


    public HudSettings(Screen parent) {
        super(Text.literal("Hud Settings"));
    }

    @Override
    protected void init() {
        super.init();
        assert this.client != null;
        this.client.keyboard.setRepeatEvents(true);
        windows.clear();
        windows.add(new Window(width / 8,
                height / 8,
                width / 8 + (width - width / 4),
                height / 8 + (height - height / 4), "Drag Me!", false));

        int w = windows.get(0).x2 - windows.get(0).x1,
                h = windows.get(0).y2 - windows.get(0).y1;
        int maxY = MathHelper.clamp(h / 4 + 119, 0, h - 22);


        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 9 + 62, w / 2 + 100, h / 9 + 82, "Toggle HUD Stats", () -> {
                    assert client != null;
                    if (ShowHUDStats()) {
                        ShowHUDStats = false;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Hud stats disabled!"));
                    } else {
                        ShowHUDStats = true;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Hud stats enabled!"));
                    }


                }));
        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 9 + 62, w / 2 - 2, h / 9 + 82, "Toggle HUD", () -> {
                    assert client != null;
                    if (ShowHUD()){
                        ShowHUD = false;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Hud disabled!"));
                    } else {
                        ShowHUD = true;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Hud enabled!"));
                    }

                }));


        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, maxY, w / 2 - 2, maxY + 20, "Exit", () -> {
                    assert client != null;

                    windows.clear();

                }));
        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, maxY, w / 2 + 100, maxY + 20, I18n.translate("Back"), () -> {
                    assert client != null;
                    client.setScreen(new HudOptions(false));
                }));

    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);

        super.render(matrices, mouseX, mouseY, delta);
    }

    public void onRenderWindow(MatrixStack matrix, int window, int mX, int mY) {
        super.onRenderWindow(matrix, window, mX, mY);


    }


}
