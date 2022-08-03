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

import static liteplus.mods.Crasher.onCrasher;
import static liteplus.mods.Crasher.playerCrasher;
import static liteplus.mods.Creative.onCreative;
import static liteplus.mods.DeathExplorer.ExploreDeath;
import static liteplus.mods.DeathExplorer.onDeathExplore;
import static liteplus.mods.Flight.FlightEnabled;
import static liteplus.mods.Flight.onFlight;
import static liteplus.mods.Invisible.onInvisible;
import static liteplus.mods.Invisible.playerInvisible;
import static liteplus.mods.NightVision.NightVision;
import static liteplus.mods.NightVision.onNightVision;
import static liteplus.mods.Overclock.TimerActive;
import static liteplus.mods.Overclock.onOverClock;
import static liteplus.mods.PlayerCopy.CopyPlayer;

@Environment(EnvType.CLIENT)
public class CheatScreen extends AbstractWindowScreen {
    private static String FlightVarName = "§cOFF";
    private static String NightVarName = "§cOFF";
    private static String OverclockVarName = "§cOFF";
    private static String CrasherVarName = "§cOFF";
    private static String InvisibleVarName = "§cOFF";
    private static String CreativeVarName = "§cOFF";
    private static String DeathVarName = "§cOFF";;

    public CheatScreen(Screen parent) {
        super(Text.literal("Cheats"));
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
                new WindowButton(w / 2 - 100, h / 15 + 47, w / 2 - 2, h / 15 + 67, "Crasher §7[" + CrasherVarName + "§7]", () -> {
                    assert client.player != null;
                    if (playerCrasher) {
                        CrasherVarName = "§cOFF";
                    } else {
                        CrasherVarName = "§aON";
                    }
                    onCrasher();
                    windows.clear();
                    client.setScreen(new CheatScreen(this));
                }));


        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 15 + 47, w / 2 + 100, h / 15 + 67, "Copy player", () -> {
                    CopyPlayer();
                    windows.clear();
                    client.setScreen(new CheatScreen(this));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 10 + 67, w / 2 + 100, h / 10 + 87, "Invisible §7[" + InvisibleVarName + "§7]", () -> {
                    assert client.player != null;
                    if (playerInvisible) {
                        InvisibleVarName = "§cOFF";
                    } else {
                        InvisibleVarName = "§aON";
                    }
                    onInvisible();
                    windows.clear();
                    client.setScreen(new CheatScreen(this));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 10 + 67, w / 2 - 2, h / 10 + 87, "Creative §7[" + CreativeVarName + "§7]", () -> {
                    assert client.interactionManager != null;
                    if (client.interactionManager.getCurrentGameMode().isCreative()){
                        CreativeVarName = "§cOFF";
                    } else {
                        CreativeVarName = "§aON";
                    }
                    onCreative();
                    windows.clear();
                    client.setScreen(new CheatScreen(this));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 4 + 57, w / 2 + 100, h / 4 + 77, "Ghost mode §7[" + DeathVarName + "§7]", () -> {
                    if (ExploreDeath) {
                        DeathVarName = "§cOFF";
                    } else {
                        DeathVarName = "§aON";
                    }
                    onDeathExplore();
                    windows.clear();
                    client.setScreen(new CheatScreen(this));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 4 + 57, w / 2 - 2, h / 4 + 77, "Overclock §7[" + OverclockVarName + "§7]", () -> {
                    if (TimerActive) {
                        OverclockVarName = "§cOFF";
                    } else {
                        OverclockVarName = "§aON";
                    }
                    onOverClock();
                    windows.clear();
                    client.setScreen(new CheatScreen(this));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 4 + 85, w / 2 - 2, h / 4 + 106, I18n.translate("Night Vision §7[" + NightVarName + "§7]"), () -> {
                    if (NightVision) {
                        NightVarName = "§cOFF";
                    } else {
                        NightVarName = "§aON";
                    }
                    onNightVision();
                    windows.clear();
                    client.setScreen(new CheatScreen(this));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 4 + 85, w / 2 + 100, h / 4 + 106, I18n.translate("Flight §7[" + FlightVarName + "§7]"), () -> {
                    if (FlightEnabled) {
                        FlightVarName = "§cOFF";
                    } else {
                        FlightVarName = "§aON";
                    }
                    onFlight();
                    windows.clear();
                    client.setScreen(new CheatScreen(this));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, maxY, w / 2 - 2, maxY + 20, "Exit", () -> {
                    windows.clear();

                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, maxY, w / 2 + 100, maxY + 20, I18n.translate("Back"), () -> {
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
