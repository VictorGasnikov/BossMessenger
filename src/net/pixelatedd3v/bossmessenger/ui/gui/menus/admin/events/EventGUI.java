package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.events;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.events.Event;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.MainGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class EventGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

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
			session.openUI(new EventGUI(session));
		}
	};

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can view and manage your events."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 45, 46, 47, 48, 49, 50, 51, 52, 53));
	}

	public EventGUI(UISession session) {
		super(session, "Event Manager", COMPONENTS);
		List<GUIComponent> inv = getComponents();
		int eventCount = 0;
		for (Event event : Settings.EVENTS) {
			boolean enabled = event.isEnabled();
			inv.add(new GUIComponent(ItemUtils.createStack(Material.REDSTONE_TORCH_ON, 1, "§e" + event.getName()), 18 + eventCount));
			inv.add(new GUIComponent(ItemUtils.createStack(Material.INK_SACK, 1, (short) (enabled ? 10 : 8), enabled ? Lang.ENABLED : Lang.DISABLED), 27 + eventCount));
			inv.add(new GUIComponent(ItemUtils.createStack(Material.PAPER, 1, ChatColor.translateAlternateColorCodes('&', event.getText())), 36 + eventCount++));
		}

	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		switch (slot) {
			case 0:
				back.run();
				break;
			default:
				if (slot < 45) {
					if (slot >= 36) {
						int eventNum = slot - 36;
						if (Settings.EVENTS.size() > eventNum) {
							Event event = Settings.EVENTS.get(eventNum);
							session.openUI(new EventMessageGUI(session, event));
						}
					} else if (slot >= 27) {
						int eventNum = slot - 27;
						if (Settings.EVENTS.size() > eventNum) {
							Event event = Settings.EVENTS.get(eventNum);
							Actions.toggleEvent(event);
							cancel.run();
						}
					}
				}
				break;
		}
	}
}
