package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.scheduler;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUI;
import net.pixelatedd3v.bossmessenger.ui.gui.chat.ChatUtils;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AlertDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.ConfirmDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.ShortPrompt;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TaskGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new TaskGUI(session, task));
		}
	};
	private int page;
	private boolean hasPrev;
	private boolean hasNext;
	private Task task;

	static {
		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can manage this task", "and it's commands"));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 3, "§f"), 9, 10, 11, 12, 13, 14, 15, 16, 17));
		COMPONENTS.addAll(GUIUtils.duplicateComponents(ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 13, "§f"), 48, 49, 50, 51, 52));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.NETHER_STAR, 1, "§aAdd command"), 45));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.NAME_TAG, 1, "§eRename task"), 46));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.BARRIER, 1, "§cDelete task"), 47));
	}

	public TaskGUI(UISession session, Task task) {
		this(session, task, 0);
	}

	public TaskGUI(UISession session, Task task, int page) {
		super(session, null, new ArrayList<GUIComponent>(COMPONENTS));
		this.page = page;
		this.task = task;
		List<GUIComponent> inv = getComponents();
		List<String> cmds = task.getCommands();
		ItemStack message = ItemUtils.createStack(Material.PAPER, 1, ChatColor.translateAlternateColorCodes('&', task.getMessage()), "", Lang.CLICK_TO_EDIT);
		inv.add(new GUIComponent(message, 53));
		int totalPages = GUIUtils.getPages(cmds, 27);
		setTitle("Task §0" + task.getName() + " §2(" + (page + 1) + "/" + (totalPages + 1) + ")");
		int slot = 18;
		int startIndex = page * 27;
		for (int time = startIndex; time < startIndex + 27; time++) {
			if (cmds.size() > time) {
				inv.add(new GUIComponent(ItemUtils.createStack(Material.COMMAND, 1, "§e/" + cmds.get(time), "", Lang.CLICK_TO_REMOVE), slot++));
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
		final String taskname = task.getName();
		switch (slot) {
			case 0:
				session.openUI(new TaskEditGUI(session));
				break;
			case 47:
				Runnable deleteOk = new Runnable() {

					@Override
					public void run() {
						Actions.deleteTask(taskname);
						session.openUI(new TaskEditGUI(session));
					}
				};
				session.openUI(new ConfirmDialog(session, deleteOk, back, "Delete task §e" + taskname + "§b?"));
				break;
			case 46:
				AnvilEnterListener enter = new AnvilEnterListener() {

					@Override
					public void onEnter(String newTaskname) {
						if (GUIUtils.isProperName(newTaskname)) {
							if (GUIUtils.isProperNameLength(newTaskname)) {
								if (!Settings.TASKS.containsKey(newTaskname)) {
									Actions.renameTask(task, newTaskname);
									back.run();
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
				session.openUI(new ShortPrompt(session, enter, back, ItemUtils.createStack(Material.PAPER, 1, taskname), "Enter your task's new name"));
				break;
			case 45:
				ChatUtils.chatUIMessage(session.getPlayer(), "Enter your command (without /)");
				session.openUI(new ChatUI(session) {

					@Override
					public void onChat(String cmd) {
						Actions.addTaskCmd(task, cmd);
						back.run();
					}

					@Override
					public void onCancel() {
						back.run();
					}
				});
				break;
			case 3:
				if (hasPrev) {
					session.openUI(new TaskGUI(session, task, page - 1));
				}
				break;
			case 5:
				if (hasNext) {
					session.openUI(new TaskGUI(session, task, page + 1));
				}
				break;
			case 53:
				ChatUtils.chatUIMessage(session.getPlayer(), "Enter your new value");
				session.openUI(new ChatUI(session) {

					@Override
					public void onChat(String message) {
						Actions.changeTaskMessage(task, message);
						back.run();
					}

					@Override
					public void onCancel() {
						back.run();
					}
				});
				break;
			default:
				if (slot > 17 && slot < 45) {
					final int id = page * 27 + slot - 18;
					List<String> cmds = task.getCommands();
					if (cmds.size() > id) {
						session.openUI(new ConfirmDialog(session, new Runnable() {

							@Override
							public void run() {
								Actions.removeTaskCmd(task, id);
								back.run();
							}
						}, back, "Delete command " + cmds.get(id)));
					}
				}
				break;
		}
	}
}
