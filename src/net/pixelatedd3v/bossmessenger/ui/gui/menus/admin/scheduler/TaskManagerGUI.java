package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.scheduler;

import net.pixelatedd3v.bossmessenger.BossMessenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import net.pixelatedd3v.bossmessenger.messenger.task.ScheduledTask;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new TaskManagerListGUI(session));
		}
	};
	private Runnable cancel = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new TaskManagerGUI(session, messenger));
		}
	};
	private Messenger messenger;
	private BukkitTask updater;
	private ScheduledTask task;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can view you task's progress,", "and cancel it."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 45, 46, 47, 48, 50, 51, 52, 53));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.BARRIER, 1, "§cCancel"), 49));
	}

	public TaskManagerGUI(UISession session, Messenger messenger) {
		super(session, messenger != null ? "Task for list §0" + messenger.getName() : "Global Task", COMPONENTS);
		this.messenger = messenger;
		if (messenger != null) {
			task = messenger.getScheduledTask();
		} else {
			task = MessengerManager.GLOBALTASK;
			if (BossMessenger.MESSENGERS.size() > 0) {
				messenger = BossMessenger.MESSENGERS.values().iterator().next();
			}
		}
		List<GUIComponent> inv = getComponents();
		long finishTime = task.getTime() + task.getMessage().getShow() * 50;
		long leftTime = finishTime - System.currentTimeMillis();
		inv.add(new GUIComponent(ItemUtils.createStack(Material.WATCH, 1, "§eTime Left", "§f" + MessageUtils.getTimer((leftTime / 1000) + 1)), 30));
		inv.add(new GUIComponent(ItemUtils.createStack(Material.PAPER, 1, "§aMessage", "§f" + messenger != null ? messenger.getScheduleMessage().getText() : "§cNone"), 32));
		updater = new BukkitRunnable() {

			@Override
			public void run() {
				ScheduledTask task;
				if (TaskManagerGUI.this.messenger != null) {
					task = TaskManagerGUI.this.messenger.getScheduledTask();
				} else {
					task = MessengerManager.GLOBALTASK;
				}
				if (task != null) {
					cancel.run();
				} else {
					back.run();
				}
			}
		}.runTaskLater(BossMessenger.INSTANCE, 20);
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		switch (slot) {
			case 0:
				back.run();
				break;
			case 49:
				Actions.cancelTask(task);
				back.run();
				break;
			case 30:
				// editMessage(message, session, MessagePart.PERCENT);
				break;
			case 32:
				// editMessage(message, session, MessagePart.SHOW);
				break;
			default:
				break;

		}
	}

	@Override
	public void onLeave(boolean isPlayerClose) {
		if (updater != null) {
			updater.cancel();
		}
	}
}
