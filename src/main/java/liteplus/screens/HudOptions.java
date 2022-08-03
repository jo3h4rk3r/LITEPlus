package liteplus.screens;

import liteplus.utils.file.GithubReader;
import liteplus.window.AbstractWindowScreen;
import liteplus.window.Window;
import liteplus.window.WindowButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.List;

import static liteplus.litepluscore.SmartTrees;
import static liteplus.litepluscore.isChunksLoading;


@Environment(EnvType.CLIENT)
public class HudOptions extends AbstractWindowScreen {



    public static boolean testVar = false;
    public static int SkyColorSet;
    public static int PreRenderFrameCount = 3;
    public static String SkyColorSetName = "§30";
    public static String Chunkycock = "§cOFF";
    private static String SmartTreeSwitch = "§aON";
    private static String prfVar = "§cOFF";


    public HudOptions(boolean parent) {
        super(Text.literal("Lite Options"));
    }

    public String playerName;

    @Override
    protected void init() {
        super.init();
        assert this.client != null;
            windows.clear();
            windows.add(new Window(width / 8,
                    height / 8,
                    width / 8 + (width - width / 4),
                    height / 8 + (height - height / 4), "Drag Me!", false));

            int w = windows.get(0).x2 - windows.get(0).x1,
                    h = windows.get(0).y2 - windows.get(0).y1;
            int maxY = MathHelper.clamp(h / 4 + 119, 0, h - 22);



        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 15 + 47, w / 2 - 2, h / 15 + 67, "PreRF §7[" + prfVar + "§7]", () -> {
                    if (testVar) {
                        testVar = false;
                        assert client.player != null;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c PRF has been disabled"));
                        prfVar = "§cOFF";
                    } else {
                        testVar = true;
                        assert client.player != null;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a PRF has been enabled"));
                        prfVar = "§aON";
                    }
                    windows.clear();
                    client.setScreen(new HudOptions(false));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 15 + 47, w / 2 + 100, h / 15 + 67, "PreRF Count §7[§e" + PreRenderFrameCount + "§7]", () -> {
                    if (PreRenderFrameCount == 9) {
                        PreRenderFrameCount = 0;
                    } else {
                        PreRenderFrameCount++;
                    }
                    assert client.player != null;
                    client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Pre render frame count set to §3" + PreRenderFrameCount));
                    windows.clear();
                    client.setScreen(new HudOptions(false));
                }));


        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 10 + 67, w / 2 - 2, h / 10 + 87, "Sky Color §7[" + SkyColorSetName + "§7]", () -> {
                    if (SkyColorSet == 8) {
                        SkyColorSet = 0;
                    } else {
                        SkyColorSet++;
                    }
                    if (HudOptions.SkyColorSet == 0) {
                        HudOptions.SkyColorSetName = "§30";
                    } else if (HudOptions.SkyColorSet == 1) {
                        HudOptions.SkyColorSetName = "§9Blue";
                    } else if (HudOptions.SkyColorSet == 2) {
                        HudOptions.SkyColorSetName = "§eYellow";
                    } else if (HudOptions.SkyColorSet == 3) {
                        HudOptions.SkyColorSetName = "§aGreen";
                    } else if (HudOptions.SkyColorSet == 4) {
                        HudOptions.SkyColorSetName = "§6Orange";
                    } else if (HudOptions.SkyColorSet == 5) {
                        HudOptions.SkyColorSetName = "§4Red";
                    } else if (HudOptions.SkyColorSet == 6) {
                        HudOptions.SkyColorSetName = "§5Magenta";
                    } else if (HudOptions.SkyColorSet == 7) {
                        HudOptions.SkyColorSetName = "§dPink";
                    } else if (HudOptions.SkyColorSet == 8) {
                        HudOptions.SkyColorSetName = "§0Black";
                    }
                    assert client.player != null;
                    client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Sky color set to " + SkyColorSetName));
                    windows.clear();
                    client.setScreen(new HudOptions(false));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 10 + 67, w / 2 + 100, h / 10 + 87, "Chunk limiter §7[" + Chunkycock + "§7]", () -> {
                    if (isChunksLoading) {
                        isChunksLoading = false;
                        Chunkycock = "§aON";
                        assert client.player != null;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Chunk limiter enabled"));
                    } else {
                        isChunksLoading = true;
                        Chunkycock = "§cOFF";
                        assert client.player != null;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Chunk limiter disabled"));
                    }
                    windows.clear();
                    client.setScreen(new HudOptions(false));
                }));


        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 4 + 57, w / 2 + 100, h / 4 + 77, "Hud Settings", () -> {
                    assert client != null;
                    client.setScreen(new HudSettings(this));
                }));


            windows.get(0).buttons.add(
                    new WindowButton(w / 2 + 2, h / 4 + 85, w / 2 + 100, h / 4 + 106, "Cheats", () -> {
                        assert client.player != null;
                        playerName = client.player.getName().getString();
                        List<String> allowedPlayers = GithubReader.readPlayerNames("allowedplayers.txt");
                        if (allowedPlayers.contains(playerName)) {
                            System.out.println(playerName + " found a match using " + allowedPlayers);
                            client.setScreen(new CheatScreen(this));
                        } else {
                            client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Unauthorized permission! ask _jo3 for cheat access."));
                            System.out.println(playerName + " count not find match using " + allowedPlayers);
                        }

                    }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 4 + 57, w / 2 - 2, h / 4 + 77, "Smart Trees §7[" + SmartTreeSwitch + "§7]", () -> {
                    if (SmartTrees) {
                        SmartTrees = false;
                        SmartTreeSwitch = "§cOFF";
                        client.worldRenderer.reload();
                        client.worldRenderer.cleanUp();
                        assert client.player != null;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Smart trees disabled"));
                    } else {
                        SmartTrees = true;
                        SmartTreeSwitch = "§aON";
                        client.worldRenderer.reload();
                        client.worldRenderer.cleanUp();
                        assert client.player != null;
                        client.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Smart trees enabled"));
                    }
                    windows.clear();
                    client.setScreen(new HudOptions(false));
                }));

            windows.get(0).buttons.add(
                    new WindowButton(w / 2 - 100, maxY, w / 2 - 2, maxY + 20, "Main Menu", () -> {
                        assert client != null;
                        client.setScreen(new MainMenu());

                    }));


            windows.get(0).buttons.add(
                    new WindowButton(w / 2 - 100, h / 4 + 85, w / 2 - 2, h / 4 + 106, I18n.translate("Commands"), () -> {
                        client.setScreen(new CommandScreen(this));
                    }));


            windows.get(0).buttons.add(
                    new WindowButton(w / 2 + 2, maxY, w / 2 + 100, maxY + 20, I18n.translate("Back to game"), () -> {
                        assert client != null;
                        windows.clear();
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
