package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.scheduler;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.lang.Lang;
import net.pixelatedd3v.bossmessenger.messenger.messengers.Messenger;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.pixelatedd3v.bossmessenger.ui.Actions;
import net.pixelatedd3v.bossmessenger.ui.gui.*;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AlertDialog;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.AnvilEnterListener;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.ShortPrompt;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.MainGUI;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger.ListSelectListener;
import net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.automessenger.ListSelector;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SchedulerGUI extends ChestGUI {

	private static final List<GUIComponent> COMPONENTS = new ArrayList<GUIComponent>();

	private Runnable back = new Runnable() {

		@Override
		public void run() {
			UISession session = getSession();
			session.openUI(new SchedulerGUI(session));
		}
	};

	static {
		ItemStack out = ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 4, "§f");
		COMPONENTS.addAll(GUIUtils.duplicateComponents(out, 1, 2, 3, 5, 6, 7, 8, 9, 18, 27, 36, 17, 26, 35, 44, 45, 53));

		ItemStack in = ItemUtils.createStack(Material.STAINED_GLASS_PANE, 1, (short) 5, "§f");
		COMPONENTS.addAll(GUIUtils.duplicateComponents(in, 10, 11, 12, 13, 14, 15, 16, 19, 28, 37, 25, 34, 43, 46, 47, 51, 52));

		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.SIGN, 1, "§bGlobal Schedule", "§aSchedules a task to everyone", "§eInput: Task, Time"), 30));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.WATCH, 1, "§bQuick Schedule", "§aSchedules a task to everyone for a default time", "§eInput: Task"), 31));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.MONSTER_EGG, 1, (short) 91, "§bGroup Schedule", "§aSchedules a task in a specified message list", "§eInput: Message list, Task, Time"), 32));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.BOOK_AND_QUILL, 1, "§bEdit Tasks"), 39));
		COMPONENTS.add(new GUIComponent(ItemUtils.createStack(Material.REDSTONE_COMPARATOR, 1, "§bTask Manager"), 41));

		COMPONENTS.add(GUIUtils.BACK);
		COMPONENTS.add(GUIUtils.getInfo("Here you can manage tasks, and schedule them globally,", "or for certain message lists only."));
	}

	public SchedulerGUI(UISession session) {
		super(session, "Scheduler", COMPONENTS);
	}

	@Override
	public void onClick(int slot, ClickType type, Object... params) {
		final UISession session = getSession();
		switch (slot) {
			case 0:
				session.openUI(new MainGUI(session));
				break;
			case 30:
				session.openUI(new TaskSelector(session, back, new TaskSelectListener() {

					@Override
					public void onSelect(final Task task, int id) {
						session.openUI(new ShortPrompt(session, new AnvilEnterListener() {

							@Override
							public void onEnter(String input) {
								int time;
								try {
									time = Integer.parseInt(input);
								} catch (Exception e) {
									session.openUI(new AlertDialog(session, "Task was not scheduled", GUIUtils.getAlertMessage("Specified time is not a valid positive integer"), back));
									return;
								}
								Actions.schedule(task, time);
								session.exit();
							}
						}, back, ItemUtils.createStack(Material.WATCH, 1, "Time in sec"), "Enter you time (in seconds)"));
					}
				}));
				break;
			case 31:
				session.openUI(new TaskSelector(session, back, new TaskSelectListener() {

					@Override
					public void onSelect(final Task task, int id) {
						Actions.schedule(task, Settings.SCHEDULE_DEFAULT_TIME);
						session.exit();
					}
				}));
				break;
			case 32:
				session.openUI(new TaskSelector(session, back, new TaskSelectListener() {

					@Override
					public void onSelect(final Task task, int id) {
						session.openUI(new ListSelector(session, back, new ListSelectListener() {

							@Override
							public void onSelect(final Messenger messenger, int id) {
								session.openUI(new ShortPrompt(session, new AnvilEnterListener() {

									@Override
									public void onEnter(String input) {
										int time;
										try {
											time = Integer.parseInt(input);
										} catch (Exception e) {
											session.openUI(new AlertDialog(session, "Task was not scheduled", GUIUtils.getAlertMessage(Lang.NOT_A_VALID_POSITIVE_INTEGER), back));
											return;
										}
										Actions.groupSchedule(messenger, task, time);
										session.exit();
									}
								}, back, ItemUtils.createStack(Material.WATCH, 1, "Time in sec"), "Enter you time (in seconds)"));
							}
						}));
					}
				}));
				break;
			case 39:
				session.openUI(new TaskEditGUI(session));
				break;
			case 41:
				session.openUI(new TaskManagerListGUI(session));
				break;

			default:
				break;
		}
	}
}
