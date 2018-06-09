package net.pixelatedd3v.bossmessenger.ui.gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemUtils {

	public static ItemStack createStack(Material material, int amount, short damage, String name, boolean enchant, String... lore) {
		ItemStack item = new ItemStack(material, amount, damage);
		if (enchant) {
			item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		ItemMeta meta = item.getItemMeta();
		if (enchant) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack createStack(Material material, int amount, String name, boolean enchant, String... lore) {
		ItemStack item = new ItemStack(material, amount);
		if (enchant) {
			item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		ItemMeta meta = item.getItemMeta();
		if (enchant) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack createStack(Material material, int amount, String name, String... lore) {
		return createStack(material, amount, name, false, lore);
	}

	public static ItemStack createStack(Material material, int amount, short damage, String name, String... lore) {
		return createStack(material, amount, damage, name, false, lore);
	}
}
