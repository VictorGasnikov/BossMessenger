package net.pixelatedd3v.bossmessenger.ui.gui;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.ShortPrompt;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger.MessengerModuleDescriptor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUIUtils {

	public static final GUIComponent BACK = new GUIComponent(ItemUtils.createStack(Material.REDSTONE, 1, "§cBack"), 0);
	public static final GUIComponent ARROW_NEXT = new GUIComponent(ItemUtils.createStack(Material.ARROW, 1, "§bNext"), 5);
	public static final GUIComponent ARROW_PREVIOUS = new GUIComponent(ItemUtils.createStack(Material.ARROW, 1, "§bPrevious"), 3);
	public static final ItemStack PLAYER = ItemUtils.createStack(Material.SKULL_ITEM, 1, (short) 3, "Name");
	public static final Material STRING_MATERIAL = Material.NAME_TAG;
	public static final Material INT_MATERIAL = Material.STONE_BUTTON;

	public static List<GUIComponent> duplicateComponents(ItemStack item, int... slots) {
		List<GUIComponent> components = new ArrayList<>();
		for (int slot : slots) {
			components.add(new GUIComponent(item, slot));
		}
		return components;
	}

	public static GUIComponent getInfo(int slot, String... lore) {
		String[] coloredLore = new String[lore.length];
		for (int i = 0; i < lore.length; i++) {
			coloredLore[i] = "§a" + lore[i];
		}
		return new GUIComponent(ItemUtils.createStack(Material.BOOK, 1, "§b§lInfo", coloredLore), slot);
	}

	public static GUIComponent getInfo(String... lore) {
		return getInfo(4, lore);
	}

	public static int getPages(Object[] array, int display) {
		double totalPagesD = (array.length) / (double) display;
		int totalPagesI = (int) totalPagesD;
		int pages = totalPagesD > totalPagesI ? totalPagesI : totalPagesI - 1;
		if (pages < 0) {
			pages = 0;
		}
		return pages;
	}

	public static int getPages(List<?> list, int display) {
		double totalPagesD = (list.size()) / (double) display;
		int totalPagesI = (int) totalPagesD;
		int pages = totalPagesD > totalPagesI ? totalPagesI : totalPagesI - 1;
		if (pages < 0) {
			pages = 0;
		}
		return pages;
	}

	public static ItemStack getAlertMessage(String message, short itemColor, ChatColor textColor) {
		return ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, itemColor, textColor + message, "§e[Click anywhere to continue]");
	}

	public static ItemStack getAlertMessage(String message) {
		return getAlertMessage(message, (short) 14, ChatColor.RED);
	}

	public static ItemStack getAlertSuccessMessage(String message) {
		return getAlertMessage(message, (short) 5, ChatColor.GREEN);
	}

	public static ItemStack getUpdate(String... lore) {
		if (Settings.UPDATE_AVAILABLE) {
			return ItemUtils.createStack(Material.EMERALD_BLOCK, 1, "§bUpdate Available", true, "§eNew Version: §a" + Settings.UPDATE_VERSION, "§eCurrent Version: §c" + BossMessenger.VERSION, "", "§6[Click to update]");
		} else {
			return ItemUtils.createStack(Material.REDSTONE_BLOCK, 1, "§cNo Update Available", lore);
		}
	}

	public static GUIComponent getModuleSelectorComponent(MessengerModuleDescriptor desc, int slot, MessengerModuleType selected) {
		return new GUIComponent(ItemUtils.createStack(desc.getMaterial(), 1, desc.getName(), desc.getType() == selected, desc.getDescription()), slot);
	}

	public static boolean isProperName(String name) {
		return name.matches("^[a-zA-Z\\d]+$");
	}

	public static boolean isProperNameLength(String name) {
		return name.length() <= 15;
	}

	public static void promptInt(UISession session, Runnable cancel, AnvilEnterListener listener, int oldValue) {
		session.openUI(new ShortPrompt(session, listener, cancel, ItemUtils.createStack(INT_MATERIAL, 1, Integer.toString(oldValue)), Lang.ENTER_VALUE));
	}

	public static void promptString(UISession session, Runnable cancel, AnvilEnterListener listener, String oldValue) {
		session.openUI(new ShortPrompt(session, listener, cancel, ItemUtils.createStack(STRING_MATERIAL, 1, oldValue), Lang.ENTER_VALUE));
	}

	public static String[] prefixArray(String prefix, String[] array) {
		String[] output = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			output[i] = prefix + array[i];
		}
		return output;
	}
}
