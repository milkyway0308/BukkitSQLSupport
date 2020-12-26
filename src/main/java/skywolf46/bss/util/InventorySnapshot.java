package skywolf46.bss.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import skywolf46.bss.util.bytesupportstream.ByteSupportInputStream;
import skywolf46.bss.util.bytesupportstream.ByteSupportOutputStream;

import java.util.HashMap;
import java.util.Map;

public class InventorySnapshot {
    private HashMap<Integer, ItemStack> items = new HashMap<>();

    public InventorySnapshot(Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null || item.getType() == Material.AIR)
                continue;
            items.put(i, item);
        }
    }

    public InventorySnapshot(byte[] fromByte) {
        ByteSupportInputStream bsis = new ByteSupportInputStream(fromByte);
        int size = bsis.readInt();
        for (int i = 0; i < size; i++) {
            int loc = bsis.readInt();
            byte[] arr = new byte[bsis.readInt()];
            bsis.readFully(arr);
            items.put(loc, BukkitUtil.fromByte(arr));
        }
    }

    public byte[] toByte() {
        ByteSupportOutputStream bsos = new ByteSupportOutputStream();
        bsos.writeInt(items.size());
        for (Map.Entry<Integer, ItemStack> item : items.entrySet()) {
            bsos.writeInt(item.getKey());
            byte[] bx = BukkitUtil.toByte(item.getValue());
            bsos.writeInt(bx.length);
            bsos.write(bx);
        }
        return bsos.closeStream();
    }

    public void apply(Inventory inv) {
        inv.clear();
        for (Map.Entry<Integer, ItemStack> ita : items.entrySet())
            inv.setItem(ita.getKey(), ita.getValue());
    }
}
