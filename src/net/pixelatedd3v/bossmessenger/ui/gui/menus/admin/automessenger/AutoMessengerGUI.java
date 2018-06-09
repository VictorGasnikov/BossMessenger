package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AlertDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.ShortPrompt;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.MainGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AutoMessengerGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<>();
	private static final ItemStack CREATE_LIST_PROMPT = ItemUtils.createStack(Material.PAPER, 1, "List name here");

	final Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new AutoMessengerGUI(session));
		}
	};
	private int page;
	private boolean hasPrev;
	private boolean hasNext;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can manage your message lists,", "and add/remove messages from the AutoMessenger."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 45, 46, 47, 48, 50, 51, 52, 53));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.NETHER_STAR, 1, "§aCreate"), 49));
	}

	public AutoMessengerGUI(UISession session) {
		this(session, 0);
	}

	public AutoMessengerGUI(UISession session, int page) {
		super(session, null, new ArrayList<GUIComponent>(COMPONENTS));
		this.page = page;
		List<GUIComponent> inv = getComponents();
		Messenger[] messengers = BossMessenger.MESSENGERS.values().toArray(new Messenger[0]);
		int totalPages = GUIUtils.getPages(messengers, 27);
		setTitle("AutoMessenger §2(" + (page + 1) + "/" + (totalPages + 1) + ")");
		int slot = 18;
		int startIndex = page * 27;
		for (int time = startIndex; time < startIndex + 27; time++) {
			if (messengers.length > time) {
				inv.add(new GUIComponent(ItemUtils.createStack(Material.PAPER, 1, "§e" + messengers[time].getName()), slot++));
			} else {
				break;
			}
		}
		if (totalPages > page) {
			inv.add(GUIUtils.ARROW_NEXT);
			hasNext = true;
		}
		if (page > 0) {
			inv.add(GUIUtils.ARROW_PREVIOUS);
			hasPrev = true;
		}
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		switch (slot) {
			case 0:
				session.openUI(new MainGUI(session));
				break;
			case 49:
				AnvilEnterListener enter = new AnvilEnterListener() {

					@Override
					public void onEnter(String input) {
						createList(session, input, cancel);
					}
				};
				session.openUI(new ShortPrompt(session, enter, cancel, CREATE_LIST_PROMPT, "Enter your list name"));

				break;
			case 3:
				if (hasPrev) {
					session.openUI(new AutoMessengerGUI(session, page - 1));
				}
				break;
			case 5:
				if (hasNext) {
					session.openUI(new AutoMessengerGUI(session, page + 1));
				}
				break;
			default:
				if (slot > 17 && slot < 45) {
					int id = page * 27 + slot - 18;
					Messenger[] messengers = BossMessenger.MESSENGERS.values().toArray(new Messenger[0]);
					if (messengers.length > id && messengers[id] != null) {
						session.openUI(new MessageListGUI(session, messengers[id]));
					}
				}
				break;
		}
	}

	public static void createList(UISession session, String input, Runnable cancel) {
		String listname = input.toLowerCase();
		if (GUIUtils.isProperName(listname)) {
			if (GUIUtils.isProperNameLength(listname)) {
				if (!BossMessenger.MESSENGERS.containsKey(listname)) {
					Actions.createList(listname);
					session.openUI(new AutoMessengerGUI(session));
				} else {
					session.openUI(new AlertDialog(session, "List was not created!", GUIUtils.getAlertMessage("§c§lThat list already exists!"), cancel));
				}
			} else {
				session.openUI(new AlertDialog(session, "List was not created!", GUIUtils.getAlertMessage("§c§lList name can not be longer than 15 characters!"), cancel));
			}
		} else {
			session.openUI(new AlertDialog(session, "List was not created!", GUIUtils.getAlertMessage("§c§lList name can only contain letters and numbers!"), cancel));
		}
	}
}
