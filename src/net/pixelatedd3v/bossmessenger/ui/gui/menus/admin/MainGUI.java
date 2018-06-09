package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.lang.LangUtils;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger.AutoMessengerGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.broadcast.BroadcastGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.events.EventGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.scheduler.SchedulerGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.settings.SettingsGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.user.UserGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MainGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<>();
	private static final int[] UPDATE_SLOTS = {0, 8, 45, 53};
	private static final List<String> CHECKING_UPDATE_LORE = new ArrayList<>();
	private static final List<String> NO_UPDATE_FOUND_LORE = new ArrayList<>();

	static {
		CHECKING_UPDATE_LORE.add("§6Checking...");

		NO_UPDATE_FOUND_LORE.add("§cNo Update Found");

		COMPONENTS.add(GUIUtils.getInfo("Welcome to §eBossMessenger Controller§a,", "This is the main section, where you can choose", "which section of BossMessenger you want to manage."));

		ItemStack out = ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 7, "§f");
		COMPONENTS.addAll(GUIUtils.duplicateComponents(out, 1, 2, 3, 5, 6, 7, 9, 18, 27, 36, 17, 26, 35, 44));

		ItemStack in = ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 12, "§f");
		COMPONENTS.addAll(GUIUtils.duplicateComponents(in, 10, 11, 12, 13, 14, 15, 16, 19, 28, 37, 25, 34, 43, 46, 47, 51, 52));

		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.PAPER, 1, "§bAutoMessenger"), 30));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.SIGN, 1, "§bBroadcast"), 31));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.WATCH, 1, "§bScheduler"), 32));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.REDSTONE_TORCH_ON, 1, "§bEvents"), 39));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.REDSTONE_COMPARATOR, 1, "§bSettings"), 41));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.WATER_LILY, 1, "§bPlayer GUI"), 40));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.MAP, 1, "§bPolls" + Lang.WIP), 49));
		//		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.REDSTONE_COMPARATOR, 1, "§bSettings"), 49));
	}

	public MainGUI(UISession session) {
		super(session, "BossMessenger Controller", COMPONENTS);
		getComponents().addAll(GUIUtils.duplicateComponents(GUIUtils.getUpdate("§6[Click to check for updates]"), UPDATE_SLOTS));
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		Player player = session.getPlayer();
		switch (slot) {
			case 0:
			case 8:
			case 45:
			case 53:
				if (Settings.UPDATE_AVAILABLE) {
					player.closeInventory();
					LangUtils.sendUpdateNotification(player);
				} else {
					getComponents().addAll(GUIUtils.duplicateComponents(GUIUtils.getUpdate("§6Checking..."), UPDATE_SLOTS));
					updateInventory();
					new BukkitRunnable() {

						@Override
						public void run() {
							if (Actions.checkUpdate()) {
								session.openUI(new MainGUI(session));
							} else {
								getComponents().addAll(GUIUtils.duplicateComponents(GUIUtils.getUpdate(), UPDATE_SLOTS));
								updateInventory();
							}
						}
					}.runTask(BossMessenger.INSTANCE);
				}
				break;
			case 30:
				session.openUI(new AutoMessengerGUI(session));
				break;
			case 31:
				session.openUI(new BroadcastGUI(session));
				break;
			case 32:
				session.openUI(new SchedulerGUI(session));
				break;
			case 39:
				session.openUI(new EventGUI(session));
				break;
			case 40:
				session.openUI(new UserGUI(session));
				break;
			case 41:
				session.openUI(new SettingsGUI(session));
				break;
		}
	}
}
