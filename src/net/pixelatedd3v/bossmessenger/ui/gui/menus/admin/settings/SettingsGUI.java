package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.settings;

import net.pixelatedd3v.bossmessenger.config.ConfigManager;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.messenger.messengers.modules.MessengerModuleType;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AlertDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.MainGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger.MessengerModuleDescriptor;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger.MessengerTypeSelector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SettingsGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<>();
	public static final String TITLE_PREFIX = "§b";
	public static final String INT_PREFIX = "§9";
	public static final String STRING_PREFIX = "§f";

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new MainGUI(session));
		}
	};
	private Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new SettingsGUI(session));
		}
	};

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can view and change your settings."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 5, "§f"), 46, 47, 48, 49, 50, 51, 52, 53));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 1, "§f"), 25, 26, 44));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 4, "§f"), 27, 28, 36, 37, 38, 39, 40));
	}

	public SettingsGUI(UISession session) {
		super(session, "Settings", COMPONENTS);
		List<GUIComponent> inv = getComponents();
		inv.add(new GUIComponent(getValueItem("Check Updates", Settings.CHECK_UPDATES), 18));
		inv.add(new GUIComponent(getValueItem("Random Colors", Settings.RDM_COLORS), 19));
		inv.add(new GUIComponent(getValueItem("Use Placeholder API", Settings.USE_PLACEHOLDER_API), 20));
		inv.add(new GUIComponent(getValueItem("Use MVDW Placeholder API", Settings.USE_MVDW_PLACEHOLDERS), 29));

		inv.add(new GUIComponent(getValueItem("Broadcast Default Time", Settings.BROADCAST_DEFAULT_TIME), 21));
		inv.add(new GUIComponent(getValueItem("Broadcast Percent", Settings.BROADCAST_PERCENT.getStringPercent()), 30));

		inv.add(new GUIComponent(getValueItem("Schedule Default Time", Settings.SCHEDULE_DEFAULT_TIME), 22));
		inv.add(new GUIComponent(getValueItem("Schedule Percent", Settings.SCHEDULE_PERCENT.getStringPercent()), 31));

		inv.add(new GUIComponent(getValueItem("Wither Update Interval", Settings.PROTOCOL_UPDATE_INTERVAL), 23));
		inv.add(new GUIComponent(getValueItem("Healthy Wither Distance", Settings.PROTOCOL_HEALTHY_WITHER_RANGE), 32));
		inv.add(new GUIComponent(getValueItem("Forcefield Wither Distance", Settings.PROTOCOL_DAMAGED_WITHER_RANGE), 41));

		inv.add(new GUIComponent(getValueItem("Private Message Format", ChatColor.translateAlternateColorCodes('&', Settings.PM_FORMAT)), 24));
		inv.add(new GUIComponent(getValueItem("Private Message Display Time", Settings.PM_DEFAULT_TIME), 33));
		inv.add(new GUIComponent(getValueItem("Private Message Percent", Settings.PM_PERCENT.getStringPercent()), 42));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.MAP, 1, TITLE_PREFIX + "Private Message Location", "§a" + MessengerModuleDescriptor.valueOf(Settings.PM_MODULE_TYPE).getName(), "", Lang.CLICK_TO_CHANGE), 34));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.MAP, 1, TITLE_PREFIX + "Welcome Message Location", "§a" + MessengerModuleDescriptor.valueOf(Settings.WELCOME_MODULE_TYPE).getName(), "", Lang.CLICK_TO_CHANGE), 43));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.ENDER_CHEST, 1, TITLE_PREFIX + "MySQL Settings" + Lang.WIP), 35));

		inv.add(new GUIComponent(ItemUtils.createStack(Material.BREWING_STAND_ITEM, 1, "§aReload Config"), 45));
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		switch (slot) {
			case 0:
				back.run();
				break;
			case 18:
				Settings.CHECK_UPDATES = !Settings.CHECK_UPDATES;
				ConfigManager.setConfigValue(ConfigManager.checkUpdates, Settings.CHECK_UPDATES);
				ConfigManager.save();
				cancel.run();
				break;
			case 19:
				promptString(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.RDM_COLORS = input;
						ConfigManager.setConfigValue(ConfigManager.rdmColorList, Settings.RDM_COLORS);
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.RDM_COLORS);
				break;
			case 20:
				if (!Settings.USE_PLACEHOLDER_API && !Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
					session.openUI(new AlertDialog(session, "Plugin not found", GUIUtils.getAlertMessage("PlaceholderAPI plugin was not found"), cancel));
					break;
				}
				Settings.USE_PLACEHOLDER_API = !Settings.USE_PLACEHOLDER_API;
				ConfigManager.setConfigValue(ConfigManager.usePlaceholderApi, Settings.USE_PLACEHOLDER_API);
				ConfigManager.save();
				cancel.run();
				break;
			case 29:
				if (!Settings.USE_MVDW_PLACEHOLDERS && !Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
					session.openUI(new AlertDialog(session, "Plugin not found", GUIUtils.getAlertMessage("MVdWPlaceholderAPI plugin was not found"), cancel));
					break;
				}
				Settings.USE_MVDW_PLACEHOLDERS = !Settings.USE_MVDW_PLACEHOLDERS;
				ConfigManager.setConfigValue(ConfigManager.useMvdwPlaceholdersAPI, Settings.USE_MVDW_PLACEHOLDERS);
				ConfigManager.save();
				cancel.run();
				break;
			case 21:
				promptInt(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						int show = Actions.convertPositiveInteger(input);
						if (show != -1) {
							Settings.BROADCAST_DEFAULT_TIME = show;
							ConfigManager.setConfigValue(ConfigManager.broadcastDefaultTime, show);
							ConfigManager.save();
							cancel.run();
						} else {
							session.openUI(new AlertDialog(session, Lang.VALUE_WAS_NOT_CHANGED, GUIUtils.getAlertMessage("§c" + Lang.NOT_A_VALID_POSITIVE_INTEGER), cancel));
						}
					}
				}, Settings.BROADCAST_DEFAULT_TIME);
				break;
			case 30:
				promptString(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.BROADCAST_PERCENT = new Percent(input);
						ConfigManager.setConfigValue(ConfigManager.broadcastDefaultPercent, Settings.BROADCAST_PERCENT.toString());
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.BROADCAST_PERCENT.getStringPercent());
				break;
			case 22:
				promptInt(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						int show = Actions.convertPositiveInteger(input);
						if (show != -1) {
							Settings.SCHEDULE_DEFAULT_TIME = show;
							ConfigManager.setConfigValue(ConfigManager.scheduleDefaultTime, show);
							ConfigManager.save();
							cancel.run();
						} else {
							session.openUI(new AlertDialog(session, Lang.VALUE_WAS_NOT_CHANGED, GUIUtils.getAlertMessage("§c" + Lang.NOT_A_VALID_POSITIVE_INTEGER), cancel));
						}
					}
				}, Settings.SCHEDULE_DEFAULT_TIME);
				break;
			case 31:
				promptString(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.SCHEDULE_PERCENT = new Percent(input);
						ConfigManager.setConfigValue(ConfigManager.scheduleDefaultPercent, Settings.SCHEDULE_PERCENT.toString());
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.SCHEDULE_PERCENT.getStringPercent());
				break;
			case 23:
				promptInt(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						int interval = Actions.convertPositiveInteger(input);
						if (interval != -1) {
							Settings.PROTOCOL_UPDATE_INTERVAL = interval;
							ConfigManager.setConfigValue(ConfigManager.protocolUpdateInterval, interval);
							ConfigManager.save();
							cancel.run();
						} else {
							session.openUI(new AlertDialog(session, Lang.VALUE_WAS_NOT_CHANGED, GUIUtils.getAlertMessage("§c" + Lang.NOT_A_VALID_POSITIVE_INTEGER), cancel));
						}
					}
				}, Settings.PROTOCOL_UPDATE_INTERVAL);
				break;
			case 32:
				promptInt(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						try {
							int distance = Integer.parseInt(input);
							Settings.PROTOCOL_HEALTHY_WITHER_RANGE = distance;
							ConfigManager.setConfigValue(ConfigManager.protocolHealthyWitherRange, distance);
							ConfigManager.save();
							cancel.run();
						} catch (Exception e) {
							session.openUI(new AlertDialog(session, Lang.VALUE_WAS_NOT_CHANGED, GUIUtils.getAlertMessage("§c" + Lang.NOT_A_VALID_POSITIVE_INTEGER), cancel));
						}
					}
				}, Settings.PROTOCOL_HEALTHY_WITHER_RANGE);
				break;
			case 41:
				promptInt(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						try {
							int distance = Integer.parseInt(input);
							Settings.PROTOCOL_DAMAGED_WITHER_RANGE = distance;
							ConfigManager.setConfigValue(ConfigManager.protocolDamagedWitherRange, distance);
							ConfigManager.save();
							cancel.run();
						} catch (Exception e) {
							session.openUI(new AlertDialog(session, Lang.VALUE_WAS_NOT_CHANGED, GUIUtils.getAlertMessage("§c" + Lang.NOT_A_VALID_POSITIVE_INTEGER), cancel));
						}
					}
				}, Settings.PROTOCOL_DAMAGED_WITHER_RANGE);
				break;
			case 24:
				promptString(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.PM_FORMAT = input;
						ConfigManager.setConfigValue(ConfigManager.pmFormat, Settings.PM_FORMAT);
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.PM_FORMAT);
				break;
			case 33:
				promptInt(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						try {
							int time = Integer.parseInt(input);
							Settings.PM_DEFAULT_TIME = time;
							ConfigManager.setConfigValue(ConfigManager.pmDefaultTime, time);
							ConfigManager.save();
							cancel.run();
						} catch (Exception e) {
							session.openUI(new AlertDialog(session, Lang.VALUE_WAS_NOT_CHANGED, GUIUtils.getAlertMessage("§c" + Lang.NOT_A_VALID_POSITIVE_INTEGER), cancel));
						}
					}
				}, Settings.PM_DEFAULT_TIME);
				break;
			case 42:
				promptString(new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.PM_PERCENT = new Percent(input);
						ConfigManager.setConfigValue(ConfigManager.pmDefaultPercent, Settings.PM_PERCENT);
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.PM_PERCENT.getStringPercent());
				break;
			case 34:
				session.openUI(new MessengerTypeSelector(session, Settings.PM_MODULE_TYPE, cancel) {

					@Override
					public void onSelect(MessengerModuleType type) {
						Settings.PM_MODULE_TYPE = type;
						ConfigManager.setConfigValue(ConfigManager.pmMessengerModule, Settings.PM_MODULE_TYPE.toString());
						ConfigManager.save();
						// for (Messenger messenger :
						// MessengerManager.PLAYER_MESSENGERS.values()) {
						// messenger.setModule(type);
						// }
						cancel.run();
					}
				});
				break;
			case 43:
				session.openUI(new MessengerTypeSelector(session, Settings.WELCOME_MODULE_TYPE, cancel) {

					@Override
					public void onSelect(MessengerModuleType type) {
						Settings.WELCOME_MODULE_TYPE = type;
						ConfigManager.setConfigValue(ConfigManager.pmMessengerModule, Settings.WELCOME_MODULE_TYPE.toString());
						ConfigManager.save();
						// for (Messenger messenger :
						// MessengerManager.PLAYER_MESSENGERS.values()) {
						// messenger.setModule(type);
						// }
						cancel.run();
					}
				});
				break;
			case 35:
				session.openUI(new MySqlGUI(session));
				break;
			case 45:
				session.exit();
				Actions.reload(session.getPlayer());
				break;
			default:
				break;
		}
	}

	public static ItemStack getValueItem(String title, boolean value) {
		return ItemUtils.createStack(Material.INK_SACK, 1, (short) (value ? 10 : 8), TITLE_PREFIX + title, (value ? "§a" : "§c") + value, "", Lang.CLICK_TO_TOGGLE);
	}

	public static ItemStack getValueItem(String title, int value) {
		return ItemUtils.createStack(Material.STONE_BUTTON, 1, TITLE_PREFIX + title, INT_PREFIX + Integer.toString(value), "", Lang.CLICK_TO_EDIT);
	}

	public static ItemStack getValueItem(String title, String value) {
		return ItemUtils.createStack(Material.NAME_TAG, 1, TITLE_PREFIX + title, STRING_PREFIX + value, "", Lang.CLICK_TO_EDIT);
	}

	public void promptInt(AnvilEnterListener listener, int oldValue) {
		GUIUtils.promptInt(getSession(), cancel, listener, oldValue);
	}

	public void promptString(AnvilEnterListener listener, String oldValue) {
		GUIUtils.promptString(getSession(), cancel, listener, oldValue);
	}
}
