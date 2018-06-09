package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.settings;

import net.pixelatedd3v.bossmessenger.config.ConfigManager;
import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AlertDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class MySqlGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<>();

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new SettingsGUI(session));
		}
	};
	private Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new MySqlGUI(session));
		}
	};

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can view and change your MySQL settings and credentials."));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.DIODE, 1, "§bSync from MySQL database" + Lang.WIP), 45));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 5, "§f"), 46, 47, 48, 49, 50, 51, 52, 53));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 1, "§f"), 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 34, 35, 36, 37, 43, 44));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 4, "§f"), 29, 38, 40, 42));
	}

	public MySqlGUI(UISession session) {
		super(session, "MySQL Settings", COMPONENTS);
		List<GUIComponent> inv = getComponents();
		inv.add(new GUIComponent(SettingsGUI.getValueItem("MySQL Host", Settings.MYSQL_HOST), 29));
		inv.add(new GUIComponent(SettingsGUI.getValueItem("MySQL Port", Settings.MYSQL_PORT), 30));
		inv.add(new GUIComponent(SettingsGUI.getValueItem("MySQL Database", Settings.MYSQL_DATABASE), 31));
		inv.add(new GUIComponent(SettingsGUI.getValueItem("MySQL Username", Settings.MYSQL_USERNAME), 32));
		inv.add(new GUIComponent(SettingsGUI.getValueItem("MySQL Password", Settings.MYSQL_PASSWORD), 33));
		inv.add(new GUIComponent(SettingsGUI.getValueItem("AutoSync Enabled", Settings.MYSQL_AUTOSYNC_ENABLED), 39));
		inv.add(new GUIComponent(SettingsGUI.getValueItem("AutoSync Delay", Settings.MYSQL_AUTOSYNC_DELAY), 41));
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		switch (slot) {
			case 0:
				back.run();
				break;
			case 29:
				GUIUtils.promptString(session, back, new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.MYSQL_HOST = input;
						ConfigManager.setConfigValue(ConfigManager.mySqlHost, Settings.MYSQL_HOST);
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.MYSQL_HOST);
				break;
			case 30:
				GUIUtils.promptString(session, back, new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.MYSQL_PORT = input;
						ConfigManager.setConfigValue(ConfigManager.mySqlPort, Settings.MYSQL_PORT);
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.MYSQL_PORT);
				break;
			case 31:
				GUIUtils.promptString(session, back, new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.MYSQL_DATABASE = input;
						ConfigManager.setConfigValue(ConfigManager.mySqlDatabase, Settings.MYSQL_DATABASE);
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.MYSQL_DATABASE);
				break;
			case 32:
				GUIUtils.promptString(session, back, new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.MYSQL_USERNAME = input;
						ConfigManager.setConfigValue(ConfigManager.mySqlUsername, Settings.MYSQL_USERNAME);
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.MYSQL_USERNAME);
				break;
			case 33:
				GUIUtils.promptString(session, back, new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						Settings.MYSQL_PASSWORD = input;
						ConfigManager.setConfigValue(ConfigManager.mySqlPassword, Settings.MYSQL_PASSWORD);
						ConfigManager.save();
						cancel.run();
					}
				}, Settings.MYSQL_PASSWORD);
				break;
			//			case 20:
			//				Settings.USE_PLACEHOLDER_API = !Settings.USE_PLACEHOLDER_API;
			//				ConfigManager.setConfigValue(ConfigManager.usePlaceholderApi, Settings.USE_PLACEHOLDER_API);
			//				ConfigManager.save();
			//				cancel.run();
			//				break;
			case 39:
				Settings.MYSQL_AUTOSYNC_ENABLED = !Settings.MYSQL_AUTOSYNC_ENABLED;
				ConfigManager.setConfigValue(ConfigManager.mySqlAutoSyncEnabled, Settings.MYSQL_AUTOSYNC_ENABLED);
				ConfigManager.save();
				cancel.run();
				break;
			case 41:
				GUIUtils.promptInt(session, cancel, new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						int delay = Actions.convertPositiveInteger(input);
						if (delay != -1) {
							Settings.MYSQL_AUTOSYNC_DELAY = delay;
							ConfigManager.setConfigValue(ConfigManager.mySqlAutoSyncDelay, delay);
							ConfigManager.save();
							cancel.run();
						} else {
							session.openUI(new AlertDialog(session, Lang.VALUE_WAS_NOT_CHANGED, GUIUtils.getAlertMessage("§c" + Lang.NOT_A_VALID_POSITIVE_INTEGER), cancel));
						}
					}
				}, Settings.MYSQL_AUTOSYNC_DELAY);
				break;
			case 45:
				//				session.exit();
				Actions.syncMySql(session.getPlayer());
			default:
				break;
		}
	}
}
