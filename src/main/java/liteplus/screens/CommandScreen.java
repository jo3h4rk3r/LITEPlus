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

@Environment(EnvType.CLIENT)
public class CommandScreen extends AbstractWindowScreen {


    public CommandScreen(Screen parent) {
        super(Text.literal("Commands"));
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

/*
        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 15 + 47, w / 2 - 2, h / 15 + 67, "_Empty_", () -> {



                }));




        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 15 + 47, w / 2 + 100, h / 15 + 67, "_Empty_", () -> {



                }));

 */




        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 10 + 67, w / 2 + 100, h / 10 + 87, "/home home", () -> {
                    client.player.sendCommand("home home", Text.literal("Teleports home"));

                }));


        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 10 + 67, w / 2 - 2, h / 10 + 87, "/Spawn", () -> {
                    client.player.sendCommand("spawn", Text.literal("Teleports to spawn"));
                }));













        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 4 + 57, w / 2 + 100, h / 4 + 77, "/rtp", () -> {

                    client.player.sendCommand("rtp", Text.literal("random teleport"));


                }));


/*
        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 4 + 57, w / 2 + 100, h / 4 + 77, "Button", () -> {
                }));

 */



        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 4 + 57, w / 2 - 2, h / 4 + 77, "/gamemode 1", () -> {
                    client.player.sendCommand("gamemode creative", Text.literal("gamemode creative"));
                }));

        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 4 + 85, w / 2 - 2, h / 4 + 106, I18n.translate("/tpaccept"), () -> {
                    client.player.sendCommand("tpaccept", Text.literal("gamemode creative"));

                }));


        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 4 + 85, w / 2 + 100, h / 4 + 106, I18n.translate("/sethome home"), () -> {

                    client.player.sendCommand("sethome home", Text.literal("sets a default home"));

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
