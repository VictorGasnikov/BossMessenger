package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.events;

import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.events.Event;
import net.pixelatedd3v.bossmessenger.messenger.message.Percent;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.MessagePart;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUtils;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AlertDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.ShortPrompt;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class EventMessageGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new EventGUI(session));
		}
	};
	private Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new EventMessageGUI(session, event));
		}
	};
	private Event event;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can view and manage your message values."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 45, 46, 47, 48, 49, 50, 51, 52, 53));
	}

	public EventMessageGUI(UISession session, Event event) {
		super(session, "Message", COMPONENTS);
		this.event = event;
		List<GUIComponent> inv = getComponents();
		int show = event.getShow();
		inv.add(new GUIComponent(ItemUtils.createStack(Material.LAPIS_BLOCK, 1, "§9Message", "§f" + ChatColor.translateAlternateColorCodes('&', event.getText()), "", Lang.CLICK_TO_EDIT), 30));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.GOLD_BLOCK, 1, "§ePercent", "§f" + event.getPercent().getStringPercent(), "", Lang.CLICK_TO_EDIT), 31));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.EMERALD_BLOCK, 1, "§aShow", "§bTicks: §f" + show, "§bSeconds: §f" + (show / 20D), "", Lang.CLICK_TO_EDIT), 32));
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		switch (slot) {
			case 0:
				back.run();
				break;
			case 30:
				editText();
				break;
			case 31:
				editMessage(session, MessagePart.PERCENT);
				break;
			case 32:
				editMessage(session, MessagePart.SHOW);
				break;
			default:
				break;

		}
	}

	public void editText() {
		final UISession session = getSession();
		ChatUI ui = new ChatUI(session) {

			@Override
			public void onChat(String input) {
				event.setText(input);
				Actions.saveEvent(event);
				cancel.run();
			}

			@Override
			public void onCancel() {
				cancel.run();
			}
		};
		ChatUtils.chatUIMessage(session.getPlayer(), "Enter your new value");
		session.openUI(ui);
	}

	public void editMessage(final UISession session, final MessagePart part) {
		String value = null;
		switch (part) {
			case PERCENT:
				value = event.getPercent().toString();
				break;
			case SHOW:
				value = Integer.toString(event.getShow());
				break;
			default:
				break;
		}
		ShortPrompt ui = new ShortPrompt(session, new AnvilEnterListener() {

			@Override
			public void onEnter(String input) {
				switch (part) {
					case PERCENT:
						event.setPercent(new Percent(input));
						Actions.saveEvent(event);
						break;
					case SHOW:
						int show = Actions.convertPositiveInteger(input);
						if (show != -1) {
							event.setShow(show);
							Actions.saveEvent(event);
						} else {
							session.openUI(new AlertDialog(session, "Value was not changed", GUIUtils.getAlertMessage("Your input is not a valid positive integer"), cancel));
							return;
						}
						break;
					default:
						break;
				}
				cancel.run();
			}
		}, cancel, ItemUtils.createStack(Material.PAPER, 1, value), "Enter your new value");
		session.openUI(ui);
	}
}
