/*

Broken as hell

being redone

*/
package liteplus.screens;


import liteplus.litepluscore;
import liteplus.utils.Decrypter;
import liteplus.utils.LoginManager;
import liteplus.utils.file.FileMang;
import liteplus.utils.file.GithubReader;
import liteplus.widget.Checkbox;
import liteplus.widget.TextPassFieldWidget;
import liteplus.window.AbstractWindowScreen;
import liteplus.window.Window;
import liteplus.window.WindowButton;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainMenu extends AbstractWindowScreen {




    public String loginResult = "";
    //private Session loginResult;
    //private Session loginResult;

    public static boolean customTitleScreen = false;

    public static String splash = "";
    public static List<String> versions = new ArrayList<>();

    /* login manager variables */
    public TextFieldWidget userField;
    public TextPassFieldWidget passField;
    public Checkbox checkBox;



    private List<List<String>> entries = new ArrayList<>();

    public MainMenu() {
        super(Text.translatable("narrator.screen.title"));
    }

    public void init() {




        windows.clear();
        windows.add(new Window(width / 8,
                height / 8,
                width / 8 + (width - width / 4),
                height / 8 + (height - height / 4), "version " + litepluscore.VERSION, false));
        windows.add(new Window(width / 8 + 15,
                height / 8 + 15,
                width / 8 + 15 + (width - width / 2),
                height / 8 + 15 + (height - height / 2), "Mojang accounts only! Adding microsoft auth soon.", true));
        windows.add(new Window(width / 8 + 30,
                height / 8 + 30,
                width / 8 + 30 + (width - width / 2),
                height / 8 + 30 + (height - height / 2), "Accounts", true));


        int w = windows.get(0).x2 - windows.get(0).x1,
                h = windows.get(0).y2 - windows.get(0).y1;
        int maxY = MathHelper.clamp(h / 4 + 119, 0, h - 22);

        windows.get(0).buttons.add(new WindowButton(w / 2 - 100, h / 4 + 38, w / 2 + 100, h / 4 + 58, I18n.translate("menu.singleplayer"), () ->
        {
            assert client != null;
            client.setScreen(new SelectWorldScreen(this));
        }
        ));
        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 4 + 62, w / 2 + 100, h / 4 + 82, I18n.translate("menu.multiplayer"), () -> {
                    assert client != null;
                    client.setScreen(new MultiplayerScreen(this));
                }));
        /*
        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, h / 4 + 86, w / 2 - 2, h / 4 + 106, "Protocol", () -> {

                    assert client != null;
                    client.setScreen(new ProtocolScreen(this));

                }));

         */
        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, h / 4 + 86, w / 2 + 100, h / 4 + 106, "Login", () -> {
                    windows.get(1).closed = false;
                  //  windows.get(0).closed = true;
                    selectWindow(1);
                }));
        windows.get(0).buttons.add(
                new WindowButton(w / 2 - 100, maxY, w / 2 - 2, maxY + 20, I18n.translate("Settings"), () -> {
                    assert client != null;
                    client.setScreen(new OptionsScreen(this, client.options));
                }));
        windows.get(0).buttons.add(
                new WindowButton(w / 2 + 2, maxY, w / 2 + 100, maxY + 20, I18n.translate("menu.quit"), () -> {
                    assert client != null;
                    client.scheduleStop();
                }));






        int x = windows.get(1).x1;
        int y = windows.get(1).y1;
        w = width - width / 2;
        h = height - height / 2;

        if (userField == null)
            userField = new TextFieldWidget(textRenderer, x + w / 2 - 98, y + h / 4, 196, 18, Text.literal(null));
        if (passField == null)
            passField = new TextPassFieldWidget(textRenderer, x + w / 2 - 98, y + h / 4 + 30, 196, 18, Text.literal(null));
        //userField.x = x + w / 2 - 98;
        userField.setX(x + w / 2 - 98);
        //userField.y = y + h / 4;
        userField.setY(y + h / 4);
        //passField.x = x + w / 2 - 98;
        userField.setX(x + w / 2 - 98);
        //passField.y = y + h / 4 + 30;
        userField.setY(y + h / 4 + 30);
        if (checkBox == null)
            checkBox = new Checkbox(x + w / 2 - 99, y + h / 4 + 53, width, height, Text.literal("Save Login"), ButtonWidget::onPress);
        //checkBox.x = x + w / 2 - 99;
        userField.setX(x + w / 2 - 99);
        //checkBox.y = y + h / 4 + 53;
        userField.setY(y + h / 4 + 53);
        userField.setMaxLength(32767);
        passField.setMaxLength(32767);

        windows.get(1).buttons.add(
                new WindowButton(w / 2 - 100, h / 3 + 84, w / 2 + 100, h / 3 + 104, "Done", () -> {
                    windows.get(1).closed = true;
                    selectWindow(1);
                }));
        windows.get(1).buttons.add(
                new WindowButton(w / 2 - 100, h / 3 + 62, w / 2 - 2, h / 3 + 82, "Accounts", () -> {
                    windows.get(2).closed = false;
                    selectWindow(2);
                }));
        windows.get(1).buttons.add(
                new WindowButton(w / 2 + 2, h / 3 + 62, w / 2 + 100, h / 3 + 82, "Login", () -> {
                    for (String s : FileMang.readFileLines("logins.txt")) {
                        entries.add(new ArrayList<>(Arrays.asList(s.split(":"))));
                    }



                    //AuthenticationException exception = account.login();

                   // try {
                       // loginResult = LoginHelper.createMojangSession(this.userField.getText(), this.passField.getText());
                 //   } catch (AuthenticationException e) {
                 //       e.printStackTrace();
                 //   }


                    loginResult = LoginManager.login(this.userField.getText(), this.passField.getText());


                    try {
                        Decrypter decrypter = new Decrypter(Decrypter.PASS_PHRASE);


                        String text = userField.getText() + ":" + decrypter.encrypt(passField.getText());

                        if (checkBox.checked && (loginResult.equals("\u00a7aLogin Successful!")
                                || loginResult.equals("Logged into cracked account!"))
                                && !entries.contains(new ArrayList<>(Arrays.asList(text.split(":"))))) {
                            entries.add(new ArrayList<>(Arrays.asList(text.split(":"))));
                            FileMang.createFile("logins.txt");
                            FileMang.appendFile(text, "logins.txt");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));



        if (splash.isEmpty()) {
            List<String> sp = GithubReader.readFileLines("splashes.txt");
            //System.out.println(sp);
            splash = !sp.isEmpty() ? sp.get(new Random().nextInt(sp.size())) : "";
        }

        entries.clear();
        FileMang.createFile("logins.txt");

        for (String s : FileMang.readFileLines("logins.txt")) {
            entries.add(new ArrayList<>(Arrays.asList(s.split(":"))));
        }
    }
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrix);
       // fill(matrix, 0, 0, width, height, Color.BLACK.getRGB());
        fillGradient(matrix, 0, 0, 6000, 6000, Color.HSBtoRGB(0, 0,0), Color.HSBtoRGB(0, 255, 1));

        int copyWidth = this.textRenderer.getWidth("Copyright Mojang AB. Do not distribute!") + 2;

        textRenderer.drawWithShadow(matrix, "Copyright Mojang AB. Do not distribute!", width - copyWidth, height - 13, -1);
        textRenderer.drawWithShadow(matrix, "Fabric: " + FabricLoader.getInstance().getModContainer("fabricloader").get().getMetadata().getVersion().getFriendlyString(),
                4, height - 33, -1);
        textRenderer.drawWithShadow(matrix, "Minecraft " + SharedConstants.getGameVersion().getName(), 4, height - 23, -1);
        textRenderer.drawWithShadow(matrix, "Logged in as: \u00a7a" + client.getSession().getUsername(), 4, height - 13, -1);




       // drawButton(matrix, "", 0, height - 14, width, height);
      //  drawButton(matrix, "\u00a7cX", 0, height - 13, 20, height - 1);

      //  int wid = 20;
       // for (Window w : windows) {
       //     if (w.closed)
       //         continue;
       //     DrawableHelper.fill(matrix, wid, height - 13, wid + 80 - 1, height - 1 - 1, 0xff000000);
       //     DrawableHelper.fill(matrix, wid + 1, height - 13 + 1, wid + 80, height - 1, 0xff000000);
      //      DrawableHelper.fill(matrix, wid + 1, height - 13 + 1, wid + 80 - 1, height - 1 - 1, (w.selected ? 0xffb0b0b0 : 0xFFEB3434));
      //      textRenderer.draw(matrix, w.title, wid + 2, height - 11, 0x000000);
      //      wid += 80;
       // }




        super.render(matrix, mouseX, mouseY, delta);





    }

    public void onRenderWindow(MatrixStack matrix, int window, int mX, int mY) {
        super.onRenderWindow(matrix, window, mX, mY);

        if (window == 0) {
            int x = windows.get(0).x1,
                    y = windows.get(0).y1 - 10,
                    w = width - width / 4,
                    h = height - height / 4;

            /* Main Text */
            //GL11.glPushMatrix();
            matrix.push();
            //GL11.glScaled(3, 3, 0);
            matrix.scale(3, 3, 0);
            drawStringWithShadow(matrix, this.textRenderer, "L I T E P L U S", (x + w/2 - 106)/3, (y + h/4 - 1)/3, getRainbow(1,2,10,0));
            // drawString(this.font, "LitePlus", (x + w/2 - 81)/3, (y + h/4 - 15)/3,
            // 0xffc0e0);


/*
             int[] intarray = {7, 13, 16, 22, 28, 34, 40, 46, 52, 58};
              String[] bruh = { "L I T E P L U S" };
            for (int i = 0; i < bruh.length; i++) {
           //     drawString(this.font, bruh[i], (x + w / 2 - 81) / 3 + intarray[i] - 8, (y + h / 4 - 15) / 3, 0xffc0e0);
                drawStringWithShadow(matrix, this.textRenderer, bruh[i], (x + w / 2 - 102) / 3 + intarray[i] - 8, (y + h / 4 - 3) / 3, 0xffc0e0);
            }

 */

           // matrix.scale(1d / 3d, 1d / 3d, 1);
            //GL11.glScaled(1d / 3d, 1d / 3d, 1);


            /* Version Text */
            //matrix.scale(2, 2, 1);
           // GL11.glScaled(1.5, 1.5, 1);
            //  drawCenteredString(this.font, litepluscore.VERSION, (int) ((x + w / 2) / 1.5), (int) ((y + h / 4 + 6) / 1.5), 0xffc050);
          //  textRenderer.drawWithShadow(matrix, litepluscore.VERSION, (int) ((x + w / 2) / 1.5), (int) ((y + h / 4 + 6) / 1.5), 0xffc050);
         //   drawCenteredText(matrix, textRenderer, litepluscore.VERSION, (int) ((x + w / 2) / 1.5), (int) ((y + h / 4 + 6) / 1.5), 0xffc050);
          //  drawCenteredText(matrix, this.textRenderer, "coded by: _jo3", (x + w/3 - 81)/5, (y + h/4 - 15)/3, 0xffc0e0);
          //  GL11.glScaled(1 / 1.5, 1 / 1.5, 1 / 1.5);


            /* Splash Text */


            matrix.scale(3, 3, 0);
          //  GL11.glTranslated(x + w / 2 + 80, y + h / 4 + 8, 0.0F);

          //  GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
            float float_4 = 1.8F - MathHelper.abs(MathHelper.sin(Util.getMeasuringTimeMs() % 1000L / 1000.0F * 6.2831855F) * 0.1F);
            float_4 = float_4 * 60.0F / (textRenderer.getWidth(splash) + 120);
          //  GL11.glScalef(float_4, float_4, float_4);
            matrix.scale(float_4, float_4, float_4);
            DrawableHelper.drawCenteredText( matrix,textRenderer, splash, -10, -8, 16776960);
          //  GL11.glPopMatrix();
            matrix.pop();




        } else if (window == 1) {
            int x = windows.get(1).x1,
                    y = windows.get(1).y1 - 10,
                    w = width - width / 2,
                    h = height - height / 2;

            drawStringWithShadow(matrix, textRenderer, "Email: ", x + w / 2 - 130, y + h / 4 + 15, 0xC0C0C0);
            drawStringWithShadow(matrix, textRenderer, "Password: ", x + w / 2 - 154, y + h / 4 + 45, 0xC0C0C0);

            drawStringWithShadow(matrix, textRenderer, "|  " + loginResult, x + w / 2 - 24, y + h / 4 + 65, 0xC0C0C0);

            userField.setX(x + w / 2 - 98);
            userField.setY(y + h / 4 + 10);
            passField.setX(x + w / 2 - 98);
            passField.setY(h / 4 + 40);
            checkBox.setX(w / 2 - 99);
            checkBox.setY(h / 4 + 63);

            userField.render(matrix, mX, mY, 1f);
            passField.render(matrix, mX, mY, 1f);
            checkBox.render(matrix, mX, mY, 1f);
        } else if (window == 2) {
            int x = windows.get(2).x1,
                    y = windows.get(2).y1 - 10,
                    w = width - width / 2,
                    h = height - height / 2;

            drawCenteredText(matrix, textRenderer, "\u00a7cTemporary\u2122 alt manager", x + w / 2, y + h / 4 - 30, -1);
            drawCenteredText(matrix, textRenderer, "\u00a74(accounts stored in plaintext for now)", x + w / 2, y + h / 4 - 20, -1);

            int c = 0;
            for (List<String> e : entries) {
                String text = (e.size() > 1 ? "\u00a7a" + e.get(0) + ":***" : "\u00a76" + e.get(0));
                assert client != null;
                int length = client.textRenderer.getWidth(text);

                fill(matrix, x + w / 2 - length / 2 - 1, y + h / 4 + c - 2, x + w / 2 + length / 2 + 1, y + h / 4 + c - 1, 0xFF303030);
                fill(matrix, x + w / 2 - length / 2 - 1, y + h / 4 + c + 9, x + w / 2 + length / 2 + 1, y + h / 4 + c + 10, 0xFF303030);
                fill(matrix, x + w / 2 - length / 2 - 2, y + h / 4 + c - 2, x + w / 2 - length / 2 - 1, y + h / 4 + c + 10, 0xFF303030);
                fill(matrix, x + w / 2 + length / 2 + 1, y + h / 4 + c - 2, x + w / 2 + length / 2 + 2, y + h / 4 + c + 10, 0xFF303030);
                drawCenteredText(matrix, textRenderer, "\u00a7cx", x + w / 2 + length / 2 + 9, y + h / 4 + c, -1);
                drawCenteredText(matrix, textRenderer, text, x + w / 2, y + h / 4 + c, -1);
                c += 14;
            }
        }
    }



    public boolean mouseClicked(double double_1, double double_2, int int_1) {


        if (double_1 > 0 && double_1 < 20 && double_2 > height - 14 && double_2 < height) {
            assert client != null;
            client.setScreen(this);
        }

        if (double_2 > height - 14 && double_2 < height) {
            int count = 0;
            for (Window w : windows) {
                if (!w.closed)
                    count++;
                if (count == (int) ((double_1 + 60) / 80)) {
                    selectWindow(windows.indexOf(w));
                    // w.selected = true;
                    break;
                }
            }
        }

        if (!windows.get(1).closed && windows.get(1).selected) {
            userField.mouseClicked(double_1, double_2, int_1);
            passField.mouseClicked(double_1, double_2, int_1);

            if (double_1 > checkBox.getX() && double_1 < checkBox.getX() + 10 && double_2 > checkBox.getY() && double_2 < checkBox.getY() + 10) {
                checkBox.checked = !checkBox.checked;
            }
        } else if (!windows.get(2).closed && windows.get(2).selected) {
            int x = windows.get(2).x1,
                    y = windows.get(2).y1 - 10,
                    w = width - width / 2,
                    h = height - height / 2;

            int c = 0;
            for (List<String> e : new ArrayList<>(entries)) {
                String text = (e.size() > 1 ? "\u00a7a" + e.get(0) + ":***" : "\u00a76" + e.get(0));
                assert client != null;
                int length = client.textRenderer.getWidth(text);

                if (double_1 > x + w / 2 - length / 2 - 1 && double_1 < x + w / 2 + length / 2 + 1 && double_2 > y + h / 3 + c * 14 - 2
                        && double_2 < y + h / 3 + c * 14 + 11) {
                    try {
                        userField.setText(e.get(0));
                    } catch (Exception e1) {
                        userField.setText("");
                    }
                    try {
                        Decrypter decrypter = new Decrypter(Decrypter.PASS_PHRASE);
                        passField.setText(decrypter.decrypt(e.get(1)));
                    } catch (Exception e1) {
                        passField.setText("");
                        e1.printStackTrace();
                    }
                    windows.get(2).closed = true;
                    windows.get(1).closed = false;
                    selectWindow(1);
                }

                if (double_1 > x + w / 2 + length / 2 + 4 && double_1 < x + w / 2 + length / 2 + 14 && double_2 > y + h / 4 + c * 14 - 2
                        && double_2 < y + h / 4 + c * 14 + 11) {
                    int c1 = 0;
                    String lines = "";
                    for (String l : FileMang.readFileLines("logins.txt")) {
                        if (l.trim().replace("\r", "").replace("\n", "").isEmpty())
                            continue;
                        if (c1 != c)
                            lines += l + "\r\n";
                        c1++;
                    }
                    FileMang.createEmptyFile("logins.txt");
                    FileMang.appendFile(lines, "logins.txt");
                    break;
                }
                c++;


            }
        }

        return super.mouseClicked(double_1, double_2, int_1);
    }

    public boolean charTyped(char char_1, int int_1) {
        if (!windows.get(1).closed) {
            if (userField.isFocused())
                userField.charTyped(char_1, int_1);
            if (passField.isFocused())
                passField.charTyped(char_1, int_1);
        }

        return super.charTyped(char_1, int_1);
    }

    public void tick() {
        if (!windows.get(1).closed) {
            userField.tick();
            passField.tick();
        }
    }

    private static int getRainbow(float sat, float bri, double speed, int offset) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + offset) / speed) % 360;
        return 0xff000000 | MathHelper.hsvToRgb((float) (rainbowState / 360.0), sat, bri);
    }

    public boolean keyPressed(int int_1, int int_2, int int_3) {
        if (!windows.get(1).closed) {
            if (userField.isFocused())
                userField.keyPressed(int_1, int_2, int_3);
            if (passField.isFocused())
                passField.keyPressed(int_1, int_2, int_3);
        }

        return super.keyPressed(int_1, int_2, int_3);
    }

}
