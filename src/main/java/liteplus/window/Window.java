package liteplus.window;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class Window {

    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public String title;
    public ItemStack icon;

    public boolean closed;
    public boolean selected = false;

    public List<WindowButton> buttons = new ArrayList<>();

    private boolean dragging = false;
    private int dragOffX;
    private int dragOffY;

    public int inactiveTime = 0;

    public Window(int x1, int y1, int x2, int y2, String title) {
        this(x1, y1, x2, y2, title, false);
    }

    public Window(int x1, int y1, int x2, int y2, String title, boolean closed) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.title = title;
        this.closed = closed;
    }

    public void render(MatrixStack matrix, int mX, int mY) {
        TextRenderer textRend = MinecraftClient.getInstance().textRenderer;

        if (dragging) {
            x2 = (x2 - x1) + mX - dragOffX;
            y2 = (y2 - y1) + mY - dragOffY;
            x1 = mX - dragOffX;
            y1 = mY - dragOffY;
        }

        drawBar(matrix, mX, mY, textRend);

        for (WindowButton w : buttons) {
            int bx1 = x1 + w.x1;
            int by1 = y1 + w.y1;
            int bx2 = x1 + w.x2;
            int by2 = y1 + w.y2;

            DrawableHelper.fill(matrix, bx1, by1, bx2 - 1, by2 - 1, 0xFF474747);
            DrawableHelper.fill(matrix, bx1 + 1, by1 + 1, bx2, by2, 0xff000000);
            DrawableHelper.fill(matrix, bx1 + 1, by1 + 1, bx2 - 1, by2 - 1,
                    selected && mX >= bx1 && mX <= bx2 && mY >= by1 && mY <= by2 ? 0xFF474747 : 0xFF363636);
            textRend.drawWithShadow(matrix, w.text, bx1 + (bx2 - bx1) / 2 - textRend.getWidth(w.text) / 2, by1 + (by2 - by1) / 2 - 4, -1);
        }



        /* window title */
        textRend.drawWithShadow(matrix, title, x1 + (icon == null || !selected || icon.getItem() == Items.AIR ? 4 : 15), y1 + 3, -1);

        if (inactiveTime >= 0) {
            inactiveTime--;
        }
    }

    protected void drawBar(MatrixStack matrix, int mX, int mY, TextRenderer textRend) {
        /* background and title bar */
        fillGrey(matrix, x1, y1, x2, y2);
        fillGradient(matrix, x1 + 2, y1 + 2, x2 - 2, y1 + 12, (selected ? 0xff0000ff : 0xff606060), (selected ? 0xFF00435C : 0xFF00235C));

        /* buttons */
        //fillGrey(matrix, x2 - 12, y1 + 3, x2 - 4, y1 + 11);
        textRend.draw(matrix, "x", x2 - 11, y1 + 2, 0x000000);

        //fillGrey(matrix, x2 - 22, y1 + 3, x2 - 14, y1 + 11);
        textRend.draw(matrix, "_", x2 - 21, y1 + 1, 0x000000);
    }

    public boolean shouldClose(int mX, int mY) {
        return selected && mX > x2 - 23 && mX < x2 && mY > y1 + 2 && mY < y1 + 12;
    }

    public void onMousePressed(int x, int y) {
        if (inactiveTime > 0) {
            return;
        }

        if (x > x1 + 2 && x < x2 - 2 && y > y1 + 2 && y < y1 + 12) {
            dragging = true;
            dragOffX = x - x1;
            dragOffY = y - y1;
        }

        for (WindowButton w : buttons) {
            if (x >= x1 + w.x1 && x <= x1 + w.x2 && y >= y1 + w.y1 && y <= y1 + w.y2) {
                w.action.run();
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    public void onMouseReleased(int x, int y) {
        dragging = false;
    }

    public void fillGrey(MatrixStack matrix, int x1, int y1, int x2, int y2) {
     //   DrawableHelper.fill(matrix, x1, y1, x2 - 1, y2 - 1, 0xFF0F0F0F);
     //   DrawableHelper.fill(matrix, x1 + 1, y1 + 1, x2, y2, 0xFF0F0F0F);
      //  DrawableHelper.fill(matrix, x1 + 1, y1 + 1, x2 - 1, y2 - 1, 0xFF0F0F0F);
    }

    public void fillGradient(MatrixStack matrix, int x1, int y1, int x2, int y2, int color1, int color2) {

       // DrawableHelper.fill(matrix, x1 + 1, y1 + 1, x2 - 1, y2 - 1, color2);

    }
}
