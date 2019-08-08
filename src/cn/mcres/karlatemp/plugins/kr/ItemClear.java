/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author 32798
 */
public class ItemClear {

    private static final ItemStack stack = new ItemStack(Material.PAPER);
    public static final String kr = "\u00a77KarmicRetribution REMOVEX #";
    public static String lore = "\u00a7b右键使用, 使用后可以减少KR点";

    static {
        stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 7);
        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName("\u00a7bKarmicRetribution\u00a76 reset");
        stack.setItemMeta(meta);
    }

    public static ItemStack make(int removeCounts) {
        ItemStack copy = stack.clone();
        ItemMeta meta = copy.getItemMeta();
        meta.setLore(Arrays.asList(lore, kr + removeCounts));
        copy.setItemMeta(meta);
        return copy;
    }

    public static boolean use(ItemStack stack, UUID uid) {
        if (stack == null) {
            return false;
        }
        if (stack.getType() != stack.getType()) {
            return false;
        }
        ItemMeta meta = stack.getItemMeta();
        List<String> lr = meta.getLore();
        if (lr == null) {
            return false;
        }
        if (lr.size() != 2) {
            return false;
        }
        if (lr.get(0).equals(lore)) {
            String c = lr.get(1);
            if (c.startsWith(kr)) {
                INT i = KrAPI.getKills().get(uid);
                if (i == null || i.value < 1) {
                    return false;
                }
                c = c.substring(kr.length());
                int rem;
                try {
                    rem = Integer.parseInt(c);
                } catch (Exception ee) {
                    return false;
                }
                i.value -= rem;
                if (i.value < 0) {
                    i.value = 0;
                }
                int a = stack.getAmount();
                if (a == 1) {
                    stack.setAmount(0);
                    stack.setType(Material.AIR);
                    return true;
                }
                stack.setAmount(a - 1);
            }
        }
        return false;
    }
}
