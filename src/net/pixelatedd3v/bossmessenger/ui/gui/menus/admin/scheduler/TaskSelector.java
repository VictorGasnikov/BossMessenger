package net.pixelatedd3v.bossmessenger.ui.gui.menus.admin.scheduler;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.messenger.task.Task;
import net.pixelatedd3v.bossmessenger.ui.gui.UISession;
import net.pixelatedd3v.bossmessenger.ui.gui.dialogs.SelectorMenu;
import org.bukkit.Material;

import java.util.ArrayList;

public class TaskSelector extends SelectorMenu<Task> {
	private TaskSelectListener listener;

	public TaskSelector(UISession session, Runnable back, TaskSelectListener listener) {
		super(session, "Select a task", new ArrayList<>(Settings.TASKS.values()), Material.EMPTY_MAP, back);
		this.listener = listener;
	}

	@Override
	public void onSelect(Task object, int slot) {
		listener.onSelect(object, slot);
	}

	@Override
	public String parse(Task task) {
		return task.getName();
	}
}
