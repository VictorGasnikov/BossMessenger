package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import net.pixelatedd3v.bossmessenger.messenger.messengers.MessengerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FinishSchedule extends BukkitRunnable {

	private final StandardMessenger standardMessager;
	private List<String> cmds;

	public FinishSchedule(StandardMessenger standardMessenger, List<String> cmds) {
		this.standardMessager = standardMessenger;
		this.cmds = cmds;
	}

	@Override
	public void run() {
		if (standardMessager != null) {
			this.standardMessager.isScheduling = false;
			this.standardMessager.scheduledTask = null;
			this.standardMessager.scheduleCommands = null;
			this.standardMessager.updateForAll();
		} else {
			MessengerManager.GLOBALTASK = null;
		}
		if (cmds != null) {
			CommandSender sender = Bukkit.getConsoleSender();
			for (String cmd : cmds) {
				Bukkit.dispatchCommand(sender, cmd);
			}
		}
	}

	public List<String> getCommands() {
		return cmds;
	}
}