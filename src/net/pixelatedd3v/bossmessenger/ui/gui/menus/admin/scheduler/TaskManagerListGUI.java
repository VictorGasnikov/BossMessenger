package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.scheduler;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerListGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();
	private int page;
	private boolean hasPrev;
	private boolean hasNext;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you view your currently running tasks", "and cancel them."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 45, 46, 47, 48, 50, 51, 52, 53));
	}

	public TaskManagerListGUI(UISession session) {
		this(session, 0);
	}

	public TaskManagerListGUI(UISession session, int page) {
		super(session, null, new ArrayList<GUIComponent>(COMPONENTS));
		this.page = page;
		List<GUIComponent> inv = getComponents();
		Messenger[] messengers = BossMessenger.MESSENGERS.values().toArray(new Messenger[0]);
		int totalPages = GUIUtils.getPages(messengers, 27);
		setTitle("Task Manager §2(" + (page + 1) + "/" + (totalPages + 1) + ")");
		int slot = 18;
		int startIndex = page * 27;
		for (int time = startIndex; time < startIndex + 27; time++) {
			if (messengers.length > time) {
				Messenger messenger = messengers[time];
				if (messenger.getScheduledTask() != null && messenger.getTaskCommands() != null) {
					inv.add(new GUIComponent(ItemUtils.createStack(Material.EMPTY_MAP, 1, "§e" + messenger.getName(), true, Lang.CLICK_TO_SEE_TASK), slot++));
				} else {
					inv.add(new GUIComponent(ItemUtils.createStack(Material.EMPTY_MAP, 1, "§e" + messenger.getName(), Lang.NO_SCHEDULED_TASK), slot++));
				}
			} else {
				break;
			}
		}
		if (MessengerManager.GLOBALTASK != null && MessengerManager.GLOBALTASK.isRunning()) {
			inv.add(new GUIComponent(ItemUtils.createStack(Material.EMPTY_MAP, 1, "§eMain task", true, Lang.CLICK_TO_SEE_TASK), 49));
		} else {
			inv.add(new GUIComponent(ItemUtils.createStack(Material.EMPTY_MAP, 1, "§eMain task", Lang.NO_SCHEDULED_TASK), 49));
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
				session.openUI(new SchedulerGUI(session));
				break;
			case 49:
				if (MessengerManager.GLOBALTASK != null && MessengerManager.GLOBALTASK.isRunning()) {
					session.openUI(new TaskManagerGUI(session, null));
				}
				break;
			case 3:
				if (hasPrev) {
					session.openUI(new TaskManagerListGUI(session, page - 1));
				}
				break;
			case 5:
				if (hasNext) {
					session.openUI(new TaskManagerListGUI(session, page + 1));
				}
				break;
			default:
				if (slot > 17 && slot < 45) {
					int id = page * 27 + slot - 18;
					Messenger[] messengers = BossMessenger.MESSENGERS.values().toArray(new Messenger[0]);
					if (messengers.length > id && messengers[id] != null) {
						Messenger messenger = messengers[id];
						if (messenger.getScheduledTask() != null && messenger.getTaskCommands() != null) {
							session.openUI(new TaskManagerGUI(session, messenger));
						}
					}
				}
				break;
		}
	}
}
