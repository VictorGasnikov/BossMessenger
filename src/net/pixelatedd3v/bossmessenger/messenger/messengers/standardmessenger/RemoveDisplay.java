package net.pixelatedd3v.bossmessenger.messenger.messengers.standardmessenger;

import org.bukkit.scheduler.BukkitRunnable;

public class RemoveDisplay extends BukkitRunnable {
	private final StandardMessenger standardMessager;

	public RemoveDisplay(StandardMessenger standardMessager) {
		this.standardMessager = standardMessager;
	}

	@Override
	public void run() {
		this.standardMessager.isDisplaying = false;
		this.standardMessager.updateForAll();
	}
}