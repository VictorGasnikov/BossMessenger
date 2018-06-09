package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger;

import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.MessageList;
import net.pixelatedd3v.bossmessenger.messenger.message.TemplateMessage;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.MessagePart;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUtils;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class FullMessageGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new MessageListGUI(session, messenger));
		}
	};
	private Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new FullMessageGUI(session, messenger, messageId));
		}
	};
	private Messenger messenger;
	private TemplateMessage message;
	private int messageId;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can view and manage your message."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 45, 46, 48, 50, 52, 53));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.PAPER, 1, "§aClone"), 47));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.ENDER_PEARL, 1, "§eMove"), 49));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.BARRIER, 1, "§cDelete"), 51));
	}

	public FullMessageGUI(UISession session, Messenger messenger, int messageId) {
		super(session, "Message §0#" + (messageId + 1), COMPONENTS);
		this.messenger = messenger;
		this.messageId = messageId;
		message = messenger.getMessages().getMessage(messageId);
		List<GUIComponent> inv = getComponents();
		int show = message.getShow();
		int interval = message.getInterval();
		inv.add(new GUIComponent(ItemUtils.createStack(Material.LAPIS_BLOCK, 1, "§9Message", "§f" + ChatColor.translateAlternateColorCodes('&', message.getText()), "", Lang.CLICK_TO_EDIT), 29));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.EMERALD_BLOCK, 1, "§aShow", "§bTicks: §f" + show, "§bSeconds: §f" + (show / 20D), "", Lang.CLICK_TO_EDIT), 30));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.REDSTONE_BLOCK, 1, "§cInterval", "§bTicks: §f" + interval, "§bSeconds: §f" + (interval / 20D), "", Lang.CLICK_TO_EDIT), 31));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.NAME_TAG, 1, "§eAttributes §c(Coming soon)", "" + Lang.CLICK_TO_EDIT), 33));
//		inv.add(new GUIComponent(ItemUtils.createStack(Material.GOLD_BLOCK, 1, "§ePercent", "§f" + message.getPercent().getStringPercent(), "", Lang.CLICK_TO_EDIT), 30));
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		final String listname = messenger.getName();
		switch (slot) {
			case 0:
				session.openUI(new MessageListGUI(session, messenger));
				break;
			case 47:
				Actions.addMessage(listname, message.getText(), message.getPercent(), message.getShow(), message.getInterval());
				back.run();
				break;
			case 49:
				session.openUI(new SelectorMenu<TemplateMessage>(session, listname, messenger.getMessages().getMessages(), Material.PAPER, "§e§lMove before:", back) {

					@Override
					public void onSelect(TemplateMessage message, int id) {
						Actions.moveMessage(messenger, messageId, id);
						back.run();
					}

					@Override
					public String parse(TemplateMessage message) {
						return ChatColor.translateAlternateColorCodes('&', message.getText());
					}
				});
				break;
			case 51:
				Runnable deleteOk = new Runnable() {

					@Override
					public void run() {
						Actions.deleteMessage(listname, messageId);
						session.openUI(new MessageListGUI(session, messenger));
					}
				};
				session.openUI(new ConfirmDialog(session, deleteOk, cancel, "Delete message §e#" + (messageId + 1) + "§b?"));
				break;
			case 29:
				editText();
				break;
//			case 30:
//				editMessage(session, MessagePart.PERCENT);
//				break;
			case 30:
				editMessage(session, MessagePart.SHOW);
				break;
			case 31:
				editMessage(session, MessagePart.INTERVAL);
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
				Actions.editText(messenger.getMessages(), message, input);
				session.openUI(new FullMessageGUI(session, messenger, messageId));
			}

			@Override
			public void onCancel() {
				session.openUI(new FullMessageGUI(session, messenger, messageId));
			}
		};
		ChatUtils.chatUIMessage(session.getPlayer(), "Enter your new value");
		session.openUI(ui);
	}

	public void editMessage(final UISession session, final MessagePart part) {
		String value = null;
		switch (part) {
			case PERCENT:
				value = message.getPercent().toString();
				break;
			case SHOW:
				value = Integer.toString(message.getShow());
				break;
			case INTERVAL:
				value = Integer.toString(message.getInterval());
				break;

			default:
				break;
		}
		ShortPrompt ui = new ShortPrompt(session, new AnvilEnterListener() {

			@Override
			public void onEnter(String input) {
				MessageList list = messenger.getMessages();
				switch (part) {
					case PERCENT:
						Actions.editPercent(list, message, input);
						break;
					case SHOW:
						if (!Actions.editShow(list, message, input)) {
							session.openUI(new AlertDialog(session, "Value was not changed", GUIUtils.getAlertMessage("Your input is not a valid positive integer"), cancel));
							return;
						}
						break;
					case INTERVAL:
						if (!Actions.editInterval(list, message, input)) {
							session.openUI(new AlertDialog(session, "Value was not changed", GUIUtils.getAlertMessage("Your input is not a valid integer"), cancel));
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
