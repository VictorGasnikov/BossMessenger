package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.scheduler;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUtils;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AlertDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.ShortPrompt;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TaskEditGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();
	private static final ItemStack CREATE_LIST_PROMPT = ItemUtils.createStack(Material.PAPER, 1, "List name here");

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new TaskEditGUI(session));
		}
	};
	private int page;
	private boolean hasPrev;
	private boolean hasNext;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can manage your tasks."));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 45, 46, 47, 48, 50, 51, 52, 53));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.NETHER_STAR, 1, "§aCreate"), 49));
	}

	public TaskEditGUI(UISession session) {
		this(session, 0);
	}

	public TaskEditGUI(UISession session, int page) {
		super(session, null, new ArrayList<GUIComponent>(COMPONENTS));
		this.page = page;
		List<GUIComponent> inv = getComponents();
		Task[] tasks = Settings.TASKS.values().toArray(new Task[0]);
		int totalPages = GUIUtils.getPages(tasks, 27);
		setTitle("Task Editor §2(" + (page + 1) + "/" + (totalPages + 1) + ")");
		int slot = 18;
		int startIndex = page * 27;
		for (int time = startIndex; time < startIndex + 27; time++) {
			if (tasks.length > time) {
				inv.add(new GUIComponent(ItemUtils.createStack(Material.EMPTY_MAP, 1, "§e" + tasks[time].getName()), slot++));
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
				session.openUI(new SchedulerGUI(session));
				break;
			case 49:

				AnvilEnterListener enter = new AnvilEnterListener() {

					@Override
					public void onEnter(final String taskname) {
						if (GUIUtils.isProperName(taskname)) {
							if (GUIUtils.isProperNameLength(taskname)) {
								if (!Settings.TASKS.containsKey(taskname)) {
									ChatUtils.chatUIMessage(session.getPlayer(), "Enter your task's message");
									session.openUI(new ChatUI(session) {

										@Override
										public void onChat(String text) {
											Actions.createTask(taskname, text);
											back.run();
										}

										@Override
										public void onCancel() {
											back.run();
										}
									});
								} else {
									session.openUI(new AlertDialog(session, "Task was not created!", GUIUtils.getAlertMessage("§c§lThat task already exists!"), back));
								}
							} else {
								session.openUI(new AlertDialog(session, "Task was not created!", GUIUtils.getAlertMessage("§c§lTask name can not be longer than 15 characters!"), back));
							}
						} else {
							session.openUI(new AlertDialog(session, "Task was not created!", GUIUtils.getAlertMessage("§c§lTask name can only contain letters and numbers!"), back));
						}
					}

				};
				session.openUI(new ShortPrompt(session, enter, back, CREATE_LIST_PROMPT, "Enter your task name"));

				break;
			case 3:
				if (hasPrev) {
					session.openUI(new TaskEditGUI(session, page - 1));
				}
				break;
			case 5:
				if (hasNext) {
					session.openUI(new TaskEditGUI(session, page + 1));
				}
				break;
			default:
				if (slot > 17 && slot < 45) {
					int id = page * 27 + slot - 18;
					Task[] tasks = Settings.TASKS.values().toArray(new Task[0]);
					if (tasks.length > id && tasks[id] != null) {
						session.openUI(new TaskGUI(session, tasks[id]));
					}
				}
				break;
		}
	}
}
